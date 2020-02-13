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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static com.altran.toolsbox.util.constant.FilterConstants.ORGANISM_FILTER;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents organism of the training
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "ORGANISMS")
@JsonFilter(ORGANISM_FILTER)
public class Organism implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Organism. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The name of this Organism.
	 */
	@Column
	private String name;

	/**
	 * The cost of the training by this organism.
	 */
	@Column
	private int cost;

	/**
	 * The availability of CV for this organism.
	 */
	@Column
	private boolean cvAvailability;

	/**
	 * The planning of the training by this organism.
	 */
	@Column
	private String planning;

	/**
	 * Training organized by this organism.
	 * 
	 * @see Training
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "training_id")
	private Training training;

	/**
	 * List of trainers of this organism.
	 * 
	 * @see Trainer
	 */
	@OneToMany(mappedBy = "organism", fetch = FetchType.EAGER)
	private Set<Trainer> trainer;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isCvAvailability() {
		return cvAvailability;
	}

	public void setCvAvailability(boolean cvAvailability) {
		this.cvAvailability = cvAvailability;
	}

	public String getPlanning() {
		return planning;
	}

	public void setPlanning(String planning) {
		this.planning = planning;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

}