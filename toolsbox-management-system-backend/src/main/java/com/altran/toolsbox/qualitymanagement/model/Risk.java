package com.altran.toolsbox.qualitymanagement.model;

import static com.altran.toolsbox.util.constant.FilterConstants.RISK_FILTER;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
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
	@JoinColumn(name = "risk_pilote")
	private User riskPilote;

	/**
	 * The Severity of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Severity severity;

	/**
	 * The RiskOrigin of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private RiskOrigin riskOrigin;

	/**
	 * The Satisfaction of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Satisfaction satisfaction;

	/**
	 * The Cost of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Cost cost;

	/**
	 * The Delay of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Delay delay;

	/**
	 * The NonConformity of this risk.
	 */

	@Enumerated(EnumType.STRING)
	private NonConformity nonConformity;

	/**
	 * The Frequency of this risk.
	 */
	@Enumerated(EnumType.STRING)
	private Frequency frequency;

	/**
	 * The Exposure of this risk (list with the new and old exposures).
	 */
	@OneToMany(cascade = CascadeType.REMOVE)
	@OrderBy("createdAt ASC")
	private Set<Exposure> exposures;

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
	 * The list of actions of this Risk
	 * 
	 * @see RiskAction
	 */
	@OneToMany(mappedBy = "risk")
	private Set<RiskAction> actions;
	/**
	 * The list of contingency Plan corresponding to this risk.
	 */
	@OneToMany
	private Set<Action> contingencyPlan;

	/**
	 * The list of mitigation Approach corresponding to this risk.
	 */
	@OneToMany
	private Set<Action> mitigationApproach;

	/**
	 * The creator of this Action.
	 * 
	 * @see User
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", nullable = true, foreignKey = @ForeignKey(name = "FK_CREATED_BY"))
	private User createdBy;

	/**
	 * The created date of this Action.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date createdAt;

	/**
	 * The last modified date of this Action.
	 */
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = "MM/dd/yyyy")
	private Date updatedAt;

	/**
	 * The comments of this Risk
	 * 
	 * @see Comment
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<Comment> comments;

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

	public Set<Exposure> getExposures() {
		return exposures;
	}

	public void setExposures(Set<Exposure> exposures) {
		this.exposures = exposures;
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

	public Set<RiskAction> getActions() {
		return actions;
	}

	public void setActions(Set<RiskAction> actions) {
		this.actions = actions;
	}

	public Set<Action> getContingencyPlan() {
		return contingencyPlan;
	}

	public void setContingencyPlan(Set<Action> contingencyPlan) {
		this.contingencyPlan = contingencyPlan;
	}

	public Set<Action> getMitigationApproach() {
		return mitigationApproach;
	}

	public void setMitigationApproach(Set<Action> mitigationApproach) {
		this.mitigationApproach = mitigationApproach;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Satisfaction getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Satisfaction satisfaction) {
		this.satisfaction = satisfaction;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public Delay getDelay() {
		return delay;
	}

	public void setDelay(Delay delay) {
		this.delay = delay;
	}

	public NonConformity getNonConformity() {
		return nonConformity;
	}

	public void setNonConformity(NonConformity nonConformity) {
		this.nonConformity = nonConformity;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public RiskOrigin getRiskOrigin() {
		return riskOrigin;
	}

	public void setRiskOrigin(RiskOrigin riskOrigin) {
		this.riskOrigin = riskOrigin;
	}

}
