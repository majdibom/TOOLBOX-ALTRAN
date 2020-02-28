package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.ActionStatus;
import com.altran.toolsbox.qualitymanagement.model.Origin;
import com.altran.toolsbox.qualitymanagement.model.Priority;
import com.altran.toolsbox.qualitymanagement.model.TypeAction;
import com.altran.toolsbox.qualitymanagement.model.searchfilter.ActionFilter;
import com.altran.toolsbox.qualitymanagement.repository.ActionRepository;
import com.altran.toolsbox.qualitymanagement.service.ActionService;
import com.altran.toolsbox.qualitymanagement.service.GapService;
import com.altran.toolsbox.qualitymanagement.service.RiskActionService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;

@Service
@Transactional
public class ActionServiceImpl implements ActionService {

	private final ActionRepository actionRepository;

	private UserService userService;

	private GapService gapService;

	private RiskActionService riskActionService;

	/**
	 * Constructor of ActionServiceImp
	 * 
	 * @param actionRepository
	 *            the repository of action
	 * 
	 */
	@Autowired
	public ActionServiceImpl(ActionRepository actionRepository) {
		super();
		this.actionRepository = actionRepository;
	}

	/**
	 * Changes gap service.
	 * 
	 * @param gapService
	 *            gap service.
	 */
	@Autowired
	public void setGapService(GapService gapService) {
		this.gapService = gapService;
	}

	/**
	 * Changes risk action service.
	 * 
	 * @param riskActionService
	 *            risk action service.
	 */
	@Autowired
	public void setRiskActionService(RiskActionService riskActionService) {
		this.riskActionService = riskActionService;
	}

	/**
	 * Changes user service.
	 * 
	 * @param userService
	 *            user service.
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Gets the list of all actions
	 * 
	 * @return list of all actions
	 */
	@Override
	public List<Action> findAll() {
		return actionRepository.findAll();
	}

	/**
	 * Gets the list of all actions by page
	 * 
	 * @return list of all actions by page
	 */
	@Override
	public Page<Action> findAllByPage(Pageable pageable) {
		return actionRepository.findAll(pageable);
	}

	/**
	 * Gets the list of all actions by defined gap
	 * 
	 * @param id
	 *            the id of the gap
	 * @return list of all actions with the input gap
	 */
	@Override
	public List<Action> findByGap(Long id) {
		return actionRepository.findByGap(gapService.findById(id));
	}

	/**
	 * Gets the list of all actions by there responsible manager
	 * 
	 * @param username
	 *            the userName of the responsible
	 * @return list of all actions with the input responsible
	 */
	@Override
	public Page<Action> findByResponsibleAction(String username, Pageable pageable) {
		User responsable = userService.findByUsername(username).get();
		return actionRepository.findByResponsibleAction(responsable, pageable);
	}

	/**
	 * gets one action by his id
	 * 
	 * @param id
	 *            the id of the action
	 * @return action object with the same id
	 * @throws NoSuchElementException
	 *             if no element is present with such ID
	 */
	@Override
	public Action findById(Long id) {
		Optional<Action> action = actionRepository.findById(id);
		if (action.isPresent())
			return action.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new action
	 * 
	 * @param action
	 *            the action to create
	 * @return the created action
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public Action create(Action action) {
		if (action.getId() != null && actionRepository.existsById(action.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return actionRepository.save(action);
	}

	/**
	 * Updates one action
	 * 
	 * @param id
	 *            the id of the action
	 * @param action
	 *            the new action object with the new values
	 * @return the updated action
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public Action update(Action action, Long id) {
		System.err.println("update 1");

		if (id != null && !actionRepository.existsById(id)) {
			System.err.println("update err ");

			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		action.setId(id);
		System.err.println("update final");

		/*
		 * // get the same create date as the old action(Fix null problem) Action
		 * oldAction = findById(id); Date createdDate = oldAction.getCreatedAt();
		 * action.setCreatedAt(createdDate); // get the same createdBy as the old
		 * action(Fix null problem) User createdBy = oldAction.getCreatedBy();
		 * action.setCreatedBy(createdBy);
		 */

		return actionRepository.save(action);
	}

	/**
	 * Deletes one action
	 * 
	 * @param id
	 *            the of the deleted action
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public Boolean delete(Long id) {
		if (id != null && !actionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		if (riskActionService.existActionInRisks(this.findById(id))) {
			// delete action
			actionRepository.deleteById(id);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Page<Action> simpleSearch(String term, Pageable pageable) {
		return actionRepository.simpleSearch(term, pageable);
	}

	/**
	 * Searches for actions by multiple terms
	 *
	 * @param actionFilter
	 *            action filter object with list of advanced search criteria
	 * @param pagination
	 *            information
	 */
	@Override
	public Page<Action> advancedSearch(ActionFilter actionFilter, Pageable pageable) {
		// Set probabilities null if the list is empty
		Set<ActionStatus> status = actionFilter.getStatus();
		if (status.isEmpty()) {
			status = null;
		}
		// Set riskNatures null if the list is empty
		Set<Priority> priorities = actionFilter.getPriorities();
		if (priorities.isEmpty()) {
			priorities = null;
		}
		// Set priorities null if the list is empty
		Set<TypeAction> types = actionFilter.getTypes();
		if (types.isEmpty()) {
			types = null;
		}
		// Set riskStrategies null if the list is empty
		Set<Origin> origins = actionFilter.getOrigins();
		if (origins.isEmpty()) {
			origins = null;
		}
		return actionRepository.advancedSearch(status, priorities, types, origins, pageable);
	}

}
