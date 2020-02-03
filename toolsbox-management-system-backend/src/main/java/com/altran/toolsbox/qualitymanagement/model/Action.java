package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.altran.toolsbox.util.constant.FilterConstants.ACTION_FILTER;
import com.altran.toolsbox.usermanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents Actions
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Entity
@Table(name = "ACTIONS")
@JsonFilter(ACTION_FILTER)
public class Action implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this action. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The cause of this action
	 */
	@Column(columnDefinition = "text")
	private String cause;

	/**
	 * The indentification's date of this action
	 */
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date identificationDate;

	/**
	 * The deadline of the action
	 */
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date deadline;

	/**
	 * The realization date of this action
	 */
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date realizationDate;

	/**
	 * The percentage of this action
	 */
	private byte percentage;

	/**
	 * The performance criteria of this action
	 */
	@Column(columnDefinition = "text")
	private String performanceCriteria;

	/**
	 * The efficiency criteria of this action
	 */
	@Column(columnDefinition = "text")
	private String efficiencyCriteria;

	/**
	 * The initial value of this action
	 */
	private String initialValue;

	/**
	 * The final value of this action
	 */
	private String finalValue;

	/**
	 * The efficiency of the action
	 */
	private String efficiency;

	/**
	 * Remarks about this action
	 */
	@Column(columnDefinition = "text")
	private String remarks;

	/**
	 * The notes of the action
	 */
	@Column(columnDefinition = "text")
	private String notes;

	/**
	 * The responsible of this action
	 * 
	 * @see User
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User responsibleAction;

	/**
	 * The impacts's process of the action
	 * 
	 * @see Process
	 */
	@ManyToMany
	private Set<Process> processImpacts;

	/**
	 * The gap of this action
	 * 
	 * @see Gap
	 */
	@ManyToOne
	@JoinColumn(name = "gap_id")
	private Gap gap;

	/**
	 * The status of this action
	 * 
	 * @see ActionStatus
	 */
	@Enumerated(EnumType.STRING)
	private ActionStatus actionStatus;

	/**
	 * The priority of this action
	 * 
	 * @see Priority
	 */
	@Enumerated(EnumType.STRING)
	private Priority priority;

	/**
	 * The typeAction of this action
	 * 
	 * @see TypeAction
	 */
	@Enumerated(EnumType.STRING)
	private TypeAction typeAction;

	/**
	 * The origin of this action
	 * 
	 * @see Origin
	 */
	@Enumerated(EnumType.STRING)
	private Origin origin;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIdentificationDate() {
		return identificationDate;
	}

	public void setIdentificationDate(Date identificationDate) {
		this.identificationDate = identificationDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public byte getPercentage() {
		return percentage;
	}

	public void setPercentage(byte percentage) {
		this.percentage = percentage;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getPerformanceCriteria() {
		return performanceCriteria;
	}

	public void setPerformanceCriteria(String performanceCriteria) {
		this.performanceCriteria = performanceCriteria;
	}

	public String getEfficiencyCriteria() {
		return efficiencyCriteria;
	}

	public void setEfficiencyCriteria(String efficiencyCriteria) {
		this.efficiencyCriteria = efficiencyCriteria;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}

	public String getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ActionStatus getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(ActionStatus actionStatus) {
		this.actionStatus = actionStatus;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public User getResponsibleAction() {
		return responsibleAction;
	}

	public void setResponsibleAction(User responsibleAction) {
		this.responsibleAction = responsibleAction;
	}

	public TypeAction getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(TypeAction typeAction) {
		this.typeAction = typeAction;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Set<Process> getProcessImpacts() {
		return processImpacts;
	}

	public void setProcessImpacts(Set<Process> processImpacts) {
		this.processImpacts = processImpacts;
	}

	public Gap getGap() {
		return gap;
	}

	public void setGap(Gap gap) {
		this.gap = gap;
	}

}
