package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.altran.toolsbox.util.constant.FilterConstants.DELIVERYMODEL_FILTER;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents Delivery model
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "DELIVERY_MODEL")
@JsonFilter(DELIVERYMODEL_FILTER)
public class DeliveryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this delivery model. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this delivery model
	 */
	@Column(unique = true)
	private String title;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
