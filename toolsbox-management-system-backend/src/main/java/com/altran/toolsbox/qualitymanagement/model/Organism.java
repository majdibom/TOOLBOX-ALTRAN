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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static com.altran.toolsbox.util.constant.FilterConstants.ORGANISM_FILTER;

import com.fasterxml.jackson.annotation.JsonFilter;
@Entity
@Table(name = "ORGANISMS")
@JsonFilter(ORGANISM_FILTER)
public class Organism implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column
	private int cost;

	@Column
	private boolean cvAvailability;

	@Column
	private String planning;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "training_id")
	private Training training;

	@OneToMany(mappedBy = "organism", fetch = FetchType.EAGER)
	private Set<Trainer> trainer;

	public Organism() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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