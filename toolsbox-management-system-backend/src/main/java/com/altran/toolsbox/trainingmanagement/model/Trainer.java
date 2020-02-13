package com.altran.toolsbox.trainingmanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static com.altran.toolsbox.util.constant.FilterConstants.TRAINER_FILTER;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents trainer of the training
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "TRAINERS")
@JsonFilter(TRAINER_FILTER)
public class Trainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Trainer. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The name of this Trainer.
	 */
	@Column
	private String name;

	/**
	 * The organism of this Trainer.
	 * 
	 * @see Organism
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organism_id")
	private Organism organism;

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

	public Organism getOrganism() {
		return organism;
	}

	public void setOrganism(Organism organism) {
		this.organism = organism;
	}

}
