package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.altran.toolsbox.usermanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents Comments
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "COMMENTS")
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this action. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * The message body of this comment
	 */
	@Column(columnDefinition = "text")
	private String message;
	
	/**
	 * The creator of this Comment.
	 * 
	 * @see User
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@CreatedBy
	@JoinColumn(name = "created_by", nullable = true, foreignKey = @ForeignKey(name = "FK_CREATED_BY"))
	private User createdBy;

	/**
	 * The created date of this Comment.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	/****** Getters and setters *****/

	
	

}
