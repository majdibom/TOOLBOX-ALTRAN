package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import static com.altran.toolsbox.util.constant.FilterConstants.GAP_FILTER;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents Gap
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "GAP")
@JsonFilter(GAP_FILTER)
public class Gap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this gap. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The description of this gap
	 */
	private String description;

	/**
	 * The justification of this gap
	 */
	@Column(columnDefinition = "text")
	private String justification;

	/**
	 * The improvement Clue (piste d'amelioration)
	 */
	@Column(columnDefinition = "text")
	private String improvementClue;

	/**
	 * The identified causes of this gap
	 */
	@Column(columnDefinition = "text")
	private String identifiedCauses;
	/**
	 * The Audit Report of this gap
	 * 
	 * @see AuditReport
	 */
	@ManyToOne
	@JoinColumn(name = "auditReport_id")
	private AuditReport auditReport;

	/**
	 * The actions of this gap
	 * 
	 * @see Action
	 */
	@OneToMany(mappedBy = "gap", cascade = CascadeType.ALL)
	private Set<Action> actions;

	/**
	 * The TypeGap of this gap
	 * 
	 * @see TypeGap
	 */
	@Enumerated(EnumType.STRING)
	private TypeGap typeGap;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getImprovementClue() {
		return improvementClue;
	}

	public void setImprovementClue(String improvementClue) {
		this.improvementClue = improvementClue;
	}

	public String getIdentifiedCauses() {
		return identifiedCauses;
	}

	public void setIdentifiedCauses(String identifiedCauses) {
		this.identifiedCauses = identifiedCauses;
	}

	public AuditReport getAuditReport() {
		return auditReport;
	}

	public void setAuditReport(AuditReport auditReport) {
		this.auditReport = auditReport;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public TypeGap getTypeGap() {
		return typeGap;
	}

	public void setTypeGap(TypeGap typeGap) {
		this.typeGap = typeGap;
	}

}
