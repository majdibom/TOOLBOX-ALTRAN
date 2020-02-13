package com.altran.toolsbox.trainingmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.altran.toolsbox.usermanagement.model.Activity;
import com.fasterxml.jackson.annotation.JsonFilter;
import static com.altran.toolsbox.util.constant.FilterConstants.TRAINING_FILTER;

/**
 * Represents training organized in the company
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "TRAINING")
@JsonFilter(TRAINING_FILTER)
public class Training implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Training. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The trainer of this Training.
	 */
	@Column
	private String trainer;

	/**
	 * The status of this Training.
	 */
	@Column
	private String status;

	/**
	 * The duration by day of this Training.
	 */
	@Column
	private int duration;

	/**
	 * The day/man cost of this Training.
	 */
	@Column
	private int dayManCost;

	/**
	 * The financial cost of this Training.
	 */
	@Column
	private int financialCost;

	/**
	 * The schedule date of this Training.
	 */
	@Column
	private Date scheduleDate;

	/**
	 * The real date of this Training.
	 */
	@Column
	private Date realDate;

	/**
	 * The activity of this Training.
	 * 
	 * @see Activity
	 */
	@ManyToMany
	private Set<Activity> activity;

	/**
	 * List of needs of this Training.
	 * 
	 * @see Need
	 */
	@OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
	private Set<Need> need;

	/**
	 * List of organisms of this Training.
	 * 
	 * @see Organism
	 */
	@OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
	private Set<Organism> organisms;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDayManCost() {
		return dayManCost;
	}

	public void setDayManCost(int dayManCost) {
		this.dayManCost = dayManCost;
	}

	public int getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(int financialCost) {
		this.financialCost = financialCost;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public Set<Activity> getActivity() {
		return activity;
	}

	public void setActivity(Set<Activity> activity) {
		this.activity = activity;
	}

	public Set<Need> getNeed() {
		return need;
	}

	public void setNeed(Set<Need> need) {
		this.need = need;
	}

	public Set<Organism> getOrganisms() {
		return organisms;
	}

	public void setOrganisms(Set<Organism> organisms) {
		this.organisms = organisms;
	}

}