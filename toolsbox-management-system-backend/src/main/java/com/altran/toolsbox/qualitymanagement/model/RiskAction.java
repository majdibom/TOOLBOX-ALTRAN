package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Represents many-to-many association between risk and action: assign action to
 * risk
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */

@Entity
@Table(name = "RISK_ACTION")
public class RiskAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The embedded ID of this RiskAction. This ID composed by the id of risk and
	 * the id of action
	 * 
	 * @see RiskActionId
	 */
	@EmbeddedId
	private RiskActionId id = new RiskActionId();

	/**
	 * The risk of this RiskAction.
	 * 
	 * @see Risk
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("riskId")
	@JoinColumn(name = "risk_id")
	private Risk risk;

	/**
	 * The action of this RiskAction.
	 * 
	 * @see Action
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("actionId")
	@JoinColumn(name = "action_id")
	private Action action;

	public RiskActionId getId() {
		return id;
	}

	public void setId(RiskActionId id) {
		this.id = id;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
