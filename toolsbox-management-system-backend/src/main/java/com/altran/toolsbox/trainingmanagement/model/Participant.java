package com.altran.toolsbox.trainingmanagement.model;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFilter;
import static com.altran.toolsbox.util.constant.FilterConstants.PARTICIPANT_FILTER;

/**
 * Represents participant in the training
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "PARTICIPANTS")
@JsonFilter(PARTICIPANT_FILTER)
public class Participant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Participant. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The firstName of this Participant.
	 */
	@Column
	private String firstName;

	/**
	 * The lastName of this Participant.
	 */
	@Column
	private String lastName;

	/**
	 * List of needs of this Participant.
	 * 
	 * @see Need
	 */
	@ManyToMany(mappedBy = "participants")
	private Set<Need> needs;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Need> getNeeds() {
		return needs;
	}

	public void setNeeds(Set<Need> needs) {
		this.needs = needs;
	}

}