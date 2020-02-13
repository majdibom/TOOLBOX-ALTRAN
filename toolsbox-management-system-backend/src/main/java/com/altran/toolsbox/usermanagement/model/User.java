package com.altran.toolsbox.usermanagement.model;

import static com.altran.toolsbox.util.constant.FilterConstants.USER_FILTER;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents users of application
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@JsonFilter(USER_FILTER)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this User. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The first name of this User.
	 */
	@Column
	private String firstName;

	/**
	 * The last name of this User.
	 */
	@Column
	private String lastName;

	/**
	 * The email of this User. This email is unique to each User.
	 */
	@Column(unique = true)
	@Email
	@NotNull
	private String email;

	/**
	 * The address of this User.
	 */
	@Column
	private String address;

	/**
	 * The phone number of this User.
	 */
	@Column
	private String phoneNumber;

	/**
	 * The birth date of this User.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	/**
	 * The userName for the account of this User. This userName is unique to each
	 * User.
	 */
	@Column(unique = true)
	private String username;

	/**
	 * The password for the account of this User.
	 */
	@Column
	private String password;

	/**
	 * The activity of this User.
	 * 
	 * @see Activity
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "FK_ACTIVITY"))
	private Activity activity;

	/**
	 * Roles list of this User.
	 * 
	 * @see Role
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Set<Role> roles;

	/**
	 * Audits list of this User.
	 * 
	 * @see Audit
	 */
	@ManyToMany(mappedBy = "audited")
	private Set<Audit> audits;

	/**
	 * Responsible actions list of this User.
	 * 
	 * @see Action
	 */
	@OneToMany(mappedBy = "responsibleAction")
	private Set<Action> actions;

	/**
	 * The account state of this User.
	 */
	@Column
	private boolean activated;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Audit> getAudits() {
		return audits;
	}

	public void setAudits(Set<Audit> audits) {
		this.audits = audits;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}