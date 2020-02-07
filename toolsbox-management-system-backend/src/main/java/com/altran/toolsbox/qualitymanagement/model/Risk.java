package com.altran.toolsbox.qualitymanagement.model;

import static com.altran.toolsbox.util.constant.FilterConstants.RISK_FILTER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.altran.toolsbox.usermanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents Risks
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Entity
@Table(name = "RISK")
@JsonFilter(RISK_FILTER)
public class Risk implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Risk. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The Probability of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Probability probability;

	/**
	 * The Risk Nature of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private RiskNature riskNature;

	/**
	 * The priority of this risk
	 * 
	 * @see Priority
	 */
	@Enumerated(EnumType.STRING)
	private Priority riskPriority;

	/**
	 * The Risk Strategy of this risk
	 * 
	 */
	@Enumerated(EnumType.STRING)
	private RiskStrategy riskStrategy;

	/**
	 * The responsible of this risk
	 * 
	 * @see User
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User riskPilote;

	/**
	 * The Severity of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Severity severity;

	/**
	 * The Exposure of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Exposure exposure;

	/**
	 * The status of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private RiskStatus riskStatus;

	/**
	 * The detection Date of this risk.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date detectionDate;

	/**
	 * The closure Date of this risk.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date closureDate;

	/**
	 * The Impact of this risk.
	 */
	private String impact;

	/**
	 * The Identification of the factors of this risk.
	 */
	private String factorsIdentif;

	/**
	 * The list of actions corresponding to this risk.
	 */
	@OneToMany
	private List<Action> actions = new ArrayList<>();

	/**
	 * The list of contingency Plan corresponding to this risk.
	 */
	@OneToMany
	private List<Action> contingencyPlan;

	/**
	 * The list of mitigation Approach corresponding to this risk.
	 */
	@OneToMany
	private List<Action> mitigationApproach;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public RiskNature getRiskNature() {
		return riskNature;
	}

	public void setRiskNature(RiskNature riskNature) {
		this.riskNature = riskNature;
	}

	public Priority getRiskPriority() {
		return riskPriority;
	}

	public void setRiskPriority(Priority riskPriority) {
		this.riskPriority = riskPriority;
	}

	public RiskStrategy getRiskStrategy() {
		return riskStrategy;
	}

	public void setRiskStrategy(RiskStrategy riskStrategy) {
		this.riskStrategy = riskStrategy;
	}

	public User getRiskPilote() {
		return riskPilote;
	}

	public void setRiskPilote(User riskPilote) {
		this.riskPilote = riskPilote;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Exposure getExposure() {
		return exposure;
	}

	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}

	public RiskStatus getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(RiskStatus riskStatus) {
		this.riskStatus = riskStatus;
	}

	public Date getDetectionDate() {
		return detectionDate;
	}

	public void setDetectionDate(Date detectionDate) {
		this.detectionDate = detectionDate;
	}

	public Date getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getFactorsIdentif() {
		return factorsIdentif;
	}

	public void setFactorsIdentif(String factorsIdentif) {
		this.factorsIdentif = factorsIdentif;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Action> getContingencyPlan() {
		return contingencyPlan;
	}

	public void setContingencyPlan(List<Action> contingencyPlan) {
		this.contingencyPlan = contingencyPlan;
	}

	public List<Action> getMitigationApproach() {
		return mitigationApproach;
	}

	public void setMitigationApproach(List<Action> mitigationApproach) {
		this.mitigationApproach = mitigationApproach;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
