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
 * @author Majdi.BEN.OTHMEN
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
	 * The Description of this action
	 */
	@Column(columnDefinition = "text")
	private String description;

	/**
	 * The Effectiveness measurement criterion of this action
	 */
	@Column(columnDefinition = "text")
	private String effMeasCriterion;

	/**
	 * The open date of this action
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date openDate;

	/**
	 * The due Date of the action
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	/**
	 * The Updated Due Date of this action
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updatedDueDate;

	/**
	 * The Effectiveness measurement date of this action
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date effMeasDate;

	/**
	 * The Realization Date of this action
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date realizationDate;

	/**
	 * comments about this action
	 */
	@Column(columnDefinition = "text")
	private String comments;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEffMeasCriterion() {
		return effMeasCriterion;
	}

	public void setEffMeasCriterion(String effMeasCriterion) {
		this.effMeasCriterion = effMeasCriterion;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getUpdatedDueDate() {
		return updatedDueDate;
	}

	public void setUpdatedDueDate(Date updatedDueDate) {
		this.updatedDueDate = updatedDueDate;
	}

	public Date getEffMeasDate() {
		return effMeasDate;
	}

	public void setEffMeasDate(Date effMeasDate) {
		this.effMeasDate = effMeasDate;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public User getResponsibleAction() {
		return responsibleAction;
	}

	public void setResponsibleAction(User responsibleAction) {
		this.responsibleAction = responsibleAction;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
