package com.altran.toolsbox.trainingmanagement.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static com.altran.toolsbox.util.constant.FilterConstants.NEED_FILTER;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents needs for training
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "NEEDS")
@JsonFilter(NEED_FILTER)
public class Need implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Need. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The object of this Need.
	 */
	@Column(nullable = false)
	private String object;

	/**
	 * The type of this Need.
	 */
	@Column(nullable = false)
	private String type;

	/**
	 * The objective of this Need.
	 */
	@Column
	private String objective;

	/**
	 * The required of this Need.
	 */
	@Column
	private String required;

	/**
	 * The total number of participants.
	 */
	@Column
	private int totalParticipants;

	/**
	 * The category of this Need.
	 */
	@Column
	private String category;

	/**
	 * The validation of this Need.
	 */
	@Column
	private String validation;

	/**
	 * List of participants in the training of this Need.
	 * 
	 * @see Participant
	 */
	@ManyToMany
	private Set<Participant> participants;

	/**
	 * The training of this Need.
	 * 
	 * @see Training
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "training_id")
	private Training training;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public int getTotalParticipants() {
		return totalParticipants;
	}

	public void setTotalParticipants(int totalParticipants) {
		this.totalParticipants = totalParticipants;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public Set<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}
}
