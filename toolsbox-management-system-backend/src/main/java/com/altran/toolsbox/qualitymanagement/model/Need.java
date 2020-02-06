package com.altran.toolsbox.qualitymanagement.model;

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

@Entity
@Table(name = "NEEDS")
@JsonFilter(NEED_FILTER)
public class Need implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private String object;

	@Column(nullable = false)
	private String type;

	@Column
	private String objectif;

	@Column
	private String required;

	@Column
	private int nbrOfParticipants;

	@Column
	private String category;

	@Column
	private String validation;

	@ManyToMany
	private Set<Participant> participants;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "training_id")
	private Training training;
	
	public Need() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public int getNbrOfParticipants() {
		return nbrOfParticipants;
	}

	public void setNbrOfParticipants(int nbrOfParticipants) {
		this.nbrOfParticipants = nbrOfParticipants;
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
