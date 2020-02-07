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

@Entity
@Table(name = "TRAININGS")
@JsonFilter(TRAINING_FILTER)
public class Training implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String trainer;

	@Column
	private String status;

	@Column
	private int dayNumber;

	@Column
	private int dayManCost;

	@Column
	private int financialCost;

	@Column
	private Date schedualDate;

	@Column
	private Date realDate;

	@ManyToMany
	private Set<Activity> activity;

	@OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
	private Set<Need> need;

	@OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
	private Set<Organism> organisms;

	public Training() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
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

	public Date getSchedualDate() {
		return schedualDate;
	}

	public void setSchedualDate(Date schedualDate) {
		this.schedualDate = schedualDate;
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