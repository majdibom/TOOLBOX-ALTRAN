package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.altran.toolsbox.util.constant.FilterConstants.PROCESS_FILTER;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents process
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Entity
@Table(name = "PROCESS")
@JsonFilter(PROCESS_FILTER)
public class Process implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this process. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this process
	 */
	@Column(unique = true)
	private String title;

	/****** Getters and setters *****/

	public Long getIdProcess() {
		return id;
	}

	public void setIdProcess(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
