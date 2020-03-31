package com.altran.toolsbox.qualitymanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.altran.toolsbox.util.constant.FilterConstants.AUDITREPORT_FILTER;
import com.altran.toolsbox.usermanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents Audit report
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Entity
@Table(name = "AUDIT_REPORT")
@JsonFilter(AUDITREPORT_FILTER)
public class AuditReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this audit report. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The realization date of the audit report
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date realizationDate;

	/**
	 * The duration of the audit
	 */
	private float duration;

	/**
	 * The examined points of the audit
	 */
	@Column(columnDefinition = "text")
	private String examinedPoints;

	/**
	 * The strong points of the audit
	 */
	@Column(columnDefinition = "text")
	private String strongPoints;

	/**
	 * The validation auditor of the audit
	 */
	private String validationAuditor;

	/**
	 * The validation auditor date of the audit
	 */
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date validationAuditorDate;

	/**
	 * The validation audited
	 */
	private String validationAudited;

	/**
	 * The validation audited date
	 */
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date validationAuditedDate;

	/**
	 * The audit of this Audit Report
	 * 
	 * @see Audit
	 */
	@OneToOne
	@JoinColumn(name = "audit_id")
	private Audit audit;

	/**
	 * The auditor of this Audit Report
	 * 
	 * @see User
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User auditor;

	/**
	 * The gaps of this Audit Report
	 * 
	 * @see Gap
	 */
	@OneToMany(mappedBy = "auditReport", cascade = CascadeType.ALL)
	private Set<Gap> gaps;

	/****** Getters and setters *****/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getExaminedPoints() {
		return examinedPoints;
	}

	public void setExaminedPoints(String examinedPoints) {
		this.examinedPoints = examinedPoints;
	}

	public String getStrongPoints() {
		return strongPoints;
	}

	public void setStrongPoints(String strongPoints) {
		this.strongPoints = strongPoints;
	}

	public String getValidationAuditor() {
		return validationAuditor;
	}

	public void setValidationAuditor(String validationAuditor) {
		this.validationAuditor = validationAuditor;
	}

	public Date getValidationAuditorDate() {
		return validationAuditorDate;
	}

	public void setValidationAuditorDate(Date validationAuditorDate) {
		this.validationAuditorDate = validationAuditorDate;
	}

	public String getValidationAudited() {
		return validationAudited;
	}

	public void setValidationAudited(String validationAudited) {
		this.validationAudited = validationAudited;
	}

	public Date getValidationAuditedDate() {
		return validationAuditedDate;
	}

	public void setValidationAuditedDate(Date validationAuditedDate) {
		this.validationAuditedDate = validationAuditedDate;
	}

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public Set<Gap> getGaps() {
		return gaps;
	}

	public void setGaps(Set<Gap> gaps) {
		this.gaps = gaps;
	}

}
