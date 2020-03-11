package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.altran.toolsbox.util.constant.FilterConstants.AUDIT_FILTER;
import com.altran.toolsbox.usermanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Represents Audits
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Entity
@Table(name = "AUDITS", uniqueConstraints = { @UniqueConstraint(columnNames = { "week_id", "project_id" }),
		@UniqueConstraint(columnNames = { "week_id", "process_id" }) })
@JsonFilter(AUDIT_FILTER)
public class Audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this audit. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The risks of this audit
	 */
	@Column(columnDefinition = "text")
	private String risks;

	/**
	 * The duration of this audit
	 */
	private float duration;

	/**
	 * The issues of this audit
	 */
	@Column(columnDefinition = "text")
	private String issues;

	/**
	 * The audit theme
	 * 
	 */
	private String auditTheme;

	/**
	 * The reference of this audit
	 * 
	 */
	private String reference;

	/**
	 * The week of this audit
	 * 
	 * @see Week
	 */
	@ManyToOne
	@JoinColumn(name = "week_id")
	private Week week;

	/**
	 * The project of this audit
	 * 
	 * @see Project
	 */
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	/**
	 * The process of this audit
	 * 
	 * @see Process
	 */
	@ManyToOne
	@JoinColumn(name = "process_id")
	private Process process;

	/**
	 * The impacts's process of the audit
	 * 
	 * @see Process
	 */
	@ManyToMany
	private Set<Process> processImpacts;

	/**
	 * The primary auditor of the audit
	 * 
	 * @see User
	 */
	@ManyToOne
	private User primaryAuditor;

	/**
	 * The Accompanying auditor of the audit
	 * 
	 * @see User
	 */
	@ManyToOne
	private User accompanyingAuditor;

	/**
	 * The auditors of the audit
	 * 
	 * @see User
	 */
	@ManyToMany
	private Set<User> audited;

	/**
	 * The report of the audit
	 * 
	 * @see AuditReport
	 */
	@OneToOne(mappedBy = "audit", cascade = CascadeType.REMOVE)
	private AuditReport auditReport;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRisks() {
		return risks;
	}

	public void setRisks(String risks) {
		this.risks = risks;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getIssues() {
		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

	public String getAuditTheme() {
		return auditTheme;
	}

	public void setAuditTheme(String auditTheme) {
		this.auditTheme = auditTheme;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public Set<Process> getProcessImpacts() {
		return processImpacts;
	}

	public void setProcessImpacts(Set<Process> processImpacts) {
		this.processImpacts = processImpacts;
	}

	public User getPrimaryAuditor() {
		return primaryAuditor;
	}

	public void setPrimaryAuditor(User primaryAuditor) {
		this.primaryAuditor = primaryAuditor;
	}

	public User getAccompanyingAuditor() {
		return accompanyingAuditor;
	}

	public void setAccompanyingAuditor(User accompanyingAuditor) {
		this.accompanyingAuditor = accompanyingAuditor;
	}

	public Set<User> getAudited() {
		return audited;
	}

	public void setAudited(Set<User> audited) {
		this.audited = audited;
	}

	public AuditReport getAuditReport() {
		return auditReport;
	}

	public void setAuditReport(AuditReport auditReport) {
		this.auditReport = auditReport;
	}

}
