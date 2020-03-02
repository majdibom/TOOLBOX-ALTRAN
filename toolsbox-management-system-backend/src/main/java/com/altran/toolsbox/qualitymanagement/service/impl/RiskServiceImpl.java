package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ColumnConstants.Faible;
import static com.altran.toolsbox.util.constant.ColumnConstants.Important;
import static com.altran.toolsbox.util.constant.ColumnConstants.Mineur;
import static com.altran.toolsbox.util.constant.ColumnConstants.Moyen;
import static com.altran.toolsbox.util.constant.ColumnConstants.Moyenne;
import static com.altran.toolsbox.util.constant.ColumnConstants.Presque_sur;
import static com.altran.toolsbox.util.constant.ColumnConstants.Très_probable;
import static com.altran.toolsbox.util.constant.ColumnConstants.Trés_Important;
import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.Comment;
import com.altran.toolsbox.qualitymanagement.model.Exposure;
import com.altran.toolsbox.qualitymanagement.model.Probability;
import com.altran.toolsbox.qualitymanagement.model.Risk;
import com.altran.toolsbox.qualitymanagement.model.RiskAction;
import com.altran.toolsbox.qualitymanagement.model.RiskActionId;
import com.altran.toolsbox.qualitymanagement.model.RiskNature;
import com.altran.toolsbox.qualitymanagement.model.RiskStatus;
import com.altran.toolsbox.qualitymanagement.model.Severity;
import com.altran.toolsbox.qualitymanagement.model.searchfilter.RiskFilter;
import com.altran.toolsbox.qualitymanagement.repository.RiskRepository;
import com.altran.toolsbox.qualitymanagement.service.ActionService;
import com.altran.toolsbox.qualitymanagement.service.CommentService;
import com.altran.toolsbox.qualitymanagement.service.RiskActionService;
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

	private RiskActionService riskActionService;

	private CommentService commentService;

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
	 * Changes risk action service.
	 * 
	 * @param riskActionService risk action service.
	 */
	@Autowired
	public void setRiskActionService(RiskActionService riskActionService) {
		this.riskActionService = riskActionService;
	}

	/**
	 * Changes comment service.
	 * 
	 * @param commentService comment service.
	 */
	@Autowired
	public void setcommentService(CommentService commentService) {
		this.commentService = commentService;
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

		/*
		 * if (risk.getId() != null && riskRepository.existsById(risk.getId())) { throw
		 * new EntityExistsException(ENTITY_EXIST); }
		 */
		// Calculate the exposure for the created risk
		risk = calculeExposure(risk);
		// New risk has "Open" status
		risk.setRiskStatus(RiskStatus.Open);
		// Create the risk
		Risk createdRisk = riskRepository.save(risk);
		try {
			Set<RiskAction> riskActions = risk.getActions();
			// Save every action in database and add it to created action list
			for (RiskAction riskAction : riskActions) {
				Action createdAction = actionService.create(riskAction.getAction());
				riskAction.setAction(createdAction);
				riskAction.setRisk(createdRisk);
				riskActionService.create(riskAction);
			}
		} catch (NullPointerException e) {

		}

		return createdRisk;
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
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		risk.setId(id);
		// Calculate the exposure for the created risk
		risk = calculeExposure(risk);
		// get the same create date as the old action(Fix null problem)
		Risk oldRisk = findById(id);
		Date createdDate = oldRisk.getCreatedAt();
		risk.setCreatedAt(createdDate);
		// get the same createdBy as the old action(Fix null problem)
		User createdBy = oldRisk.getCreatedBy();
		risk.setCreatedBy(createdBy);

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
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		riskRepository.deleteById(id);
	}

	/**
	 * Deletes one action From Risk
	 * 
	 * @param the id of the deleted action
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void deleteActionFromRisk(RiskActionId riskActionId) {
		if (riskActionId == null) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		riskActionService.delete(riskActionId);
	}

	/**
	 * Calculate the exposure (probability * severity)
	 * 
	 * @param probability the probability of the risk
	 * @param severity    the severity of the risk
	 * @return the exposure of the risk
	 */
	@Override
	public Risk calculeExposure(Risk risk) {
		Probability probability = risk.getProbability();
		Severity severity = risk.getSeverity();

		switch (probability.name()) {
		case (Faible):
			switch (severity.name()) {
			case (Mineur):
				risk.setExposure(Exposure.Acceptable);
				risk.setExposureValue(1);
				break;
			case (Moyen):
				risk.setExposure(Exposure.Acceptable);
				risk.setExposureValue(2);
				break;
			case (Important):
				risk.setExposure(Exposure.Acceptable);
				risk.setExposureValue(3);
				break;
			case (Trés_Important):
				risk.setExposure(Exposure.A_surveiller);
				risk.setExposureValue(4);
				break;
			default:
				break;
			}
			break;
		case (Moyenne):
			switch (severity.name()) {
			case (Mineur):
				risk.setExposure(Exposure.Acceptable);
				risk.setExposureValue(2);
				break;
			case (Moyen):
				risk.setExposure(Exposure.A_surveiller);
				risk.setExposureValue(4);
				break;
			case (Important):
				risk.setExposure(Exposure.A_surveiller);
				risk.setExposureValue(6);
				break;
			case (Trés_Important):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(8);
				break;
			default:
				break;
			}
			break;
		case (Très_probable):
			switch (severity.name()) {
			case (Mineur):
				risk.setExposure(Exposure.Acceptable);
				risk.setExposureValue(3);
				break;
			case (Moyen):
				risk.setExposure(Exposure.A_surveiller);
				risk.setExposureValue(6);
				break;
			case (Important):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(9);
				break;
			case (Trés_Important):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(12);
				break;
			default:
				break;
			}
			break;
		case (Presque_sur):
			switch (severity.name()) {
			case (Mineur):
				risk.setExposure(Exposure.A_surveiller);
				risk.setExposureValue(4);
				break;
			case (Moyen):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(8);
				break;
			case (Important):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(12);
				break;
			case (Trés_Important):
				risk.setExposure(Exposure.Trés_critique);
				risk.setExposureValue(16);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return risk;
	}

	@Override
	public Page<Risk> findByRiskPilote(String username, Pageable pageable) {
		User responsable = userService.findByUsername(username).get();
		return riskRepository.findByRiskPilote(responsable, pageable);
	}

	/**
	 * Searches for risks by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of risks contains the input term by page
	 */
	@Override
	public Page<Risk> simpleSearch(String term, Pageable pageable) {
		return riskRepository.simpleSearch(term, pageable);
	}

	/**
	 * Searches for risks by multiple terms
	 *
	 * @param riskFilter risk filter object with list of advanced search criteria
	 * @param pagination information
	 */
	@Override
	public Page<Risk> advancedSearch(RiskFilter riskFilter, Pageable pageable) {
		// Set probabilities null if the list is empty
		Set<Probability> probabilities = riskFilter.getProbabilities();
		if (probabilities.isEmpty()) {
			probabilities = null;
		}
		// Set riskNatures null if the list is empty
		Set<RiskNature> riskNatures = riskFilter.getRiskNatures();
		if (riskNatures.isEmpty()) {
			riskNatures = null;
		}
		// Set priorities null if the list is empty
		Set<Exposure> exposures = riskFilter.getExposures();
		if (exposures.isEmpty()) {
			exposures = null;
		}
		// Set riskStrategies null if the list is empty
		Set<RiskStatus> status = riskFilter.getStatus();
		if (status.isEmpty()) {
			status = null;
		}
		return riskRepository.advancedSearch(probabilities, riskNatures, exposures, status, pageable);
	}

	/**
	 * add comment to one risk
	 * 
	 * @param id      the id of risk
	 * @param Comment the comment object
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Comment addComment(Long id, Comment comment) {
		if (comment.getId() != null && commentService.existsById(comment.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		Comment createdComment = commentService.create(comment);
		Risk risk = findById(id);
		risk.getComments().add(createdComment);
		riskRepository.save(risk);
		return createdComment;
	}

	/**
	 * delete comment from action
	 * 
	 * @param Comment the comment object
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void deleteComment(Long id, Comment comment) {
		if (id != null && !riskRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		if (comment.getId() != null && commentService.existsById(comment.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		Risk risk = findById(id);
		risk.getComments().remove(comment);
		riskRepository.save(risk);
		commentService.delete(comment.getId());
	}

}
