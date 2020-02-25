package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents id of RiskAction: composed by id of risk and id of action
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Embeddable
public class RiskActionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of the Risk.
	 * 
	 * @see Risk
	 */
	@Column
	private Long riskId;

	/**
	 * The ID of the Action.
	 * 
	 * @see Action
	 */
	@Column
	private Long actionId;

	public Long getRiskId() {
		return riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

}
