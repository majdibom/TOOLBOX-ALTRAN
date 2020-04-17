package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static com.altran.toolsbox.util.constant.FilterConstants.WEEK_FILTER;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents week
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Entity
@Table(name = "WEEK")
@JsonFilter(WEEK_FILTER)
public class Week implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Week. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The number of this week
	 */
	private int number;

	/**
	 * The audits of this week
	 * 
	 * @see Audit
	 */
	@OneToMany(mappedBy = "week")
	private Set<Audit> audits;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Audit> getAudits() {
		return audits;
	}

	public void setAudits(Set<Audit> audits) {
		this.audits = audits;
	}

	public Week() {
		super();
	}

	public Week(int number) {
		super();
		this.number = number;
	}

}
