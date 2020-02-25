package com.altran.toolsbox.qualitymanagement.service;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.RiskAction;
import com.altran.toolsbox.qualitymanagement.model.RiskActionId;

/**
 * Represents the interface of RiskAction service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface RiskActionService {

	RiskAction create(RiskAction riskAction);

	boolean existActionInRisks(Action action);

	void delete(RiskActionId riskActionId);

}
