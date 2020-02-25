package com.altran.toolsbox.qualitymanagement.service.impl;

import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.RiskAction;
import com.altran.toolsbox.qualitymanagement.model.RiskActionId;
import com.altran.toolsbox.qualitymanagement.repository.RiskActionRepository;
import com.altran.toolsbox.qualitymanagement.service.RiskActionService;

@Service
public class RiskActionServiceImpl implements RiskActionService {

	private final RiskActionRepository riskActionRepository;

	/**
	 * Constructor of RiskActionServiceImpl
	 * 
	 * @param riskActionRepository
	 *            the repository of risk action
	 */
	@Autowired
	public RiskActionServiceImpl(RiskActionRepository riskActionRepository) {
		this.riskActionRepository = riskActionRepository;
	}

	/**
	 * Check the existence of action in risks
	 * 
	 * @param action
	 *            the action to check
	 */
	@Override
	public boolean existActionInRisks(Action action) {
		Set<RiskAction> riskActions = riskActionRepository.findByAction(action);
		return riskActions.isEmpty();
	}

	/**
	 * Creates a new risk action
	 * 
	 * @param riskAction
	 *            the risk action to create
	 * @return the created risk action
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public RiskAction create(RiskAction riskAction) {
		return riskActionRepository.save(riskAction);
	}

	/**
	 * Deletes one riskAction
	 * 
	 * @param id
	 *            the of the deleted riskAction
	 */
	@Override
	public void delete(RiskActionId riskActionId) {
		riskActionRepository.delete(riskActionRepository.findById(riskActionId).get());
	}
}
