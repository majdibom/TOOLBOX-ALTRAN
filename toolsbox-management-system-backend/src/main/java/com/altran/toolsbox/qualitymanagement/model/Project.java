package com.altran.toolsbox.qualitymanagement.model;

import static com.altran.toolsbox.util.constant.FilterConstants.PROJECT_FILTER;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.altran.toolsbox.usermanagement.model.Activity;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents project
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
@Entity
@Table(name = "PROJECT")
@JsonFilter(PROJECT_FILTER)
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this project. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this project
	 */
	private String title;

	/**
	 * The activity of this project
	 * 
	 * @see Activity
	 */
	@ManyToOne
	private Activity activity;

	/**
	 * The delivery Model of this project
	 * 
	 * @see DeliveryModel
	 */
	@ManyToOne
	private DeliveryModel deliveryModel;

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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public DeliveryModel getDeliveryModel() {
		return deliveryModel;
	}

	public void setDeliveryModel(DeliveryModel deliveryModel) {
		this.deliveryModel = deliveryModel;
	}

}
