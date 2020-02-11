package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;
import static com.altran.toolsbox.util.constant.ColumnConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.Exposure;
import com.altran.toolsbox.qualitymanagement.model.Probability;
import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.qualitymanagement.model.Severity;
import com.altran.toolsbox.qualitymanagement.repository.RiskRepository;
import com.altran.toolsbox.qualitymanagement.service.ActionService;
import com.altran.toolsbox.qualitymanagement.service.RiskService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;

/**
 * Represents implementation of activity service
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Service
public class RiskServiceImpl implements RiskService {

	private final RiskRepository riskRepository;

	private UserService userService;
	private ActionService actionService;

	/**
	 * Constructor of RiskServiceImpl
	 * 
	 * @param riskRepository the repository of risk
	 */
	@Autowired
	public RiskServiceImpl(RiskRepository riskRepository) {
		this.riskRepository = riskRepository;
	}

	/**
	 * Changes action service.
	 * 
	 * @param actionService action service.
	 */
	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	/**
	 * Gets the list of all risks
	 * 
	 * @return list of all risks
	 */
	@Override
	public List<Risk> findAll() {
		return riskRepository.findAll();
	}

	/**
	 * Gets the list of all risks
	 * 
	 * @return list of all risks
	 */
	public Page<Risk> findAllByPage(Pageable pageable) {
		return riskRepository.findAll(pageable);
	}

	/**
	 * gets one risk by his id
	 * 
	 * @param id the id of the risk
	 * @return risk object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Risk findById(Long id) {
		Optional<Risk> risk = riskRepository.findById(id);
		if (risk.isPresent())
			return risk.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new risk
	 * 
	 * @param risk the risk to create
	 * @return the created risk
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Risk create(Risk risk) {
		if (risk.getId() != null && riskRepository.existsById(risk.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		// Calculate the exposure for the created risk
		risk.setExposure(calculeExposure(risk.getProbability(), risk.getSeverity()));
		// Get action list of the created risk
		List<Action> actions = risk.getActions();
		// Empty action list in created risk
		risk.setActions(new ArrayList<Action>());
		// new action list to assign to the created risk
		List<Action> createdActions = new ArrayList<>();
		// Save every action in database and add it to created action list
		for (Action action : actions) {
			Action createdAction = actionService.create(action);
			createdActions.add(createdAction);
		}
		// Assign the new action list to the created risk
		risk.setActions(createdActions);

		return riskRepository.save(risk);
	}

	/**
	 * Updates one risk
	 * 
	 * @param id   the id of the risk
	 * @param risk the new risk object with the new values
	 * @return the updated risk
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Risk update(Risk risk, Long id) {
		if (id != null && !riskRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		risk.setId(id);
		return riskRepository.save(risk);
	}

	/**
	 * Deletes one risk
	 * 
	 * @param id the of the deleted risk
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !riskRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		riskRepository.deleteById(id);
	}

	/**
	 * Calculate the exposure (probability * severity)
	 * 
	 * @param probability the probability of the risk
	 * @param severity    the severity of the risk
	 * @return the exposure of the risk
	 */
	@Override
	public Exposure calculeExposure(Probability probability, Severity severity) {

		switch (probability.name()) {
		case (UNLIKELY):
			switch (severity.name()) {
			case (SIGNIFICANT_PROBLEM):
			case (SERIOUS_PROBLEM):
			case (WORRYING_SITUATION):
				return Exposure.Low;
			case (CRISIS):
				return Exposure.Medium;
			default:
				break;
			}
			break;
		case (LIKELY):
			switch (severity.name()) {
			case (SIGNIFICANT_PROBLEM):
				return Exposure.Low;
			case (SERIOUS_PROBLEM):
			case (WORRYING_SITUATION):
				return Exposure.Medium;
			case (CRISIS):
				return Exposure.High;
			default:
				break;
			}
			break;
		case (VERY_LIKELY):
			switch (severity.name()) {
			case (SIGNIFICANT_PROBLEM):
				return Exposure.Low;
			case (SERIOUS_PROBLEM):
				return Exposure.Medium;
			case (CRISIS):
			case (WORRYING_SITUATION):
				return Exposure.High;
			default:
				break;
			}
			break;
		case (ALMOST_SURE):
			switch (severity.name()) {
			case (SIGNIFICANT_PROBLEM):
				return Exposure.Medium;
			case (SERIOUS_PROBLEM):
			case (CRISIS):
			case (WORRYING_SITUATION):
				return Exposure.High;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return Exposure.Low;
	}

	@Override
	public Page<Risk> findByRiskPilote(String username, Pageable pageable) {
		User responsable = userService.findByUsername(username);
		return riskRepository.findByRiskPilote(responsable, pageable);
	}

	@Override
	public Page<Risk> simpleSearch(String term, Pageable pageable) {
		return riskRepository.simpleSearch(term, pageable);
	}

}
