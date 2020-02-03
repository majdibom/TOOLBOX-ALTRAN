package com.altran.toolsbox.util.constant;

public final class ColumnConstants {

	private ColumnConstants() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Common
	 */
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String CREATEDAT = "createdAt";
	public static final String UPDATEDAT = "updatedAt";
	public static final String CREATEDBY = "createdBy";
	public static final String ACTIVITY = "activity";
	public static final String ACTIONS = "actions";
	public static final String AUDITS = "audits";

	/**
	 * User Management
	 */
	// User
	public static final String FIRSTNAME = "firstName";
	public static final String LASTNAME = "lastName";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String PASSW = "password";
	public static final String PHONENUMBER = "phoneNumber";
	public static final String BIRTHDATE = "birthDate";
	public static final String USERNAME = "username";
	public static final String ACTIVATED = "activated";
	public static final String ROLES = "roles";

	// Role
	public static final String PRIVILEGES = "privileges";

	/**
	 * Quality Management
	 */
	// common
	public static final String PROCESSIMPACTS = "processImpacts";
	public static final String REALIZATIONDATE = "realizationDate";
	public static final String DURATION = "duration";
	public static final String AUDITREPORT = "auditReport";

	// Action
	public static final String IDENTIFICATIONDATE = "identificationDate";
	public static final String DEADLINE = "deadline";
	public static final String PERCENTAGE = "percentage";
	public static final String CAUSE = "cause";
	public static final String PERFORMANCECRITERIA = "performanceCriteria";
	public static final String EFFICIENCYCRITERIA = "efficiencyCriteria";
	public static final String INITIALVALUE = "initialValue";
	public static final String FINALVALUE = "finalValue";
	public static final String EFFICIENCY = "efficiency";
	public static final String REMARKS = "remarks";
	public static final String NOTES = "notes";
	public static final String RESPONSIBLEACTION = "responsibleAction";
	public static final String GAP = "gap";
	public static final String ACTIONSTATUS = "actionStatus";
	public static final String PRIORITY = "priority";
	public static final String TYPEACTION = "typeAction";
	public static final String ORIGIN = "origin";

	// Audit
	public static final String RISKS = "risks";
	public static final String ISSUES = "issues";
	public static final String AUDITTHEME = "auditTheme";
	public static final String REFERENCE = "reference";
	public static final String WEEK = "week";
	public static final String PROJECT = "project";
	public static final String PROCESS = "process";
	public static final String PRIMARYAUDITOR = "primaryAuditor";
	public static final String ACCOMPANYINGAUDITOR = "accompanyingAuditor";
	public static final String AUDITED = "audited";

	// AuditReport
	public static final String EXAMINEDPOINTS = "examinedPoints";
	public static final String STRONGPOINTS = "strongPoints";
	public static final String VALIDATIONAUDITOR = "validationAuditor";
	public static final String VALIDATIONAUDITORDATE = "validationAuditorDate";
	public static final String VALIDATIONAUDITED = "validationAudited";
	public static final String VALIDATIONAUDITEDDATE = "validationAuditedDate";
	public static final String AUDIT = "audit";
	public static final String AUDITOR = "auditor";
	public static final String GAPS = "gaps";

	// Gap
	public static final String JUSTIFICATION = "justification";
	public static final String IMPROVEMENTCLUE = "improvementClue";
	public static final String IDENTIFIEDCAUSES = "identifiedCauses";

	// Project
	public static final String DELIVERYMODEL = "deliveryModel";

	// Week
	public static final String NUMBER = "number";
	
	// Risk
	public static final String PROBABILITY ="probability" ;
	public static final String SEVERITY ="severity";
	public static final String EXPOSURE ="exposure";
	public static final String RISKSTATUS ="riskStatus" ;
	public static final String DETECTIONDATE ="detectionDate";
	public static final String CLOSUREDATE ="closureDate";
	public static final String STRATEGY ="strategy";
	public static final String ACTION ="Action" ;
	public static final String CONTINGENCYPLAN ="contingencyPlan";
	public static final String MITIGATIONAPPROACH ="mitigationApproach";
	
	// Probability
	public static final String UNLIKELY ="Unlikely" ;
	public static final String LIKELY ="Likely";
	public static final String VERY_LIKELY ="Very_Likely";
	public static final String ALMOST_SURE ="Almost_Sure" ;
	
	// Severity
	public static final String SIGNIFICANT_PROBLEM ="Significant_Problem" ;
	public static final String SERIOUS_PROBLEM ="Serious_Problem";
	public static final String WORRYING_SITUATION ="Worrying_Situation";
	public static final String CRISIS ="Crisis" ;
	
	/**
	 * Training Management
	 */

	/**
	 * Roles
	 */
	public static final String ROLEADMIN = "ADMIN";
	public static final String ROLEAUDITEUR = "AUDITEUR";
	public static final String ROLEAUDITE = "AUDITE";
	public static final String ROLERESPONSABLEQUALITE = "RESPONSABLE_QUALITE";
	public static final String ROLERESPONSABLEACTIVITE = "RESPONSABLE_ACTIVITE";
	public static final String ROLEMEMBREEQUIPEQUALITE = "MEMBRE_EQUIPE_QUALITE";
	public static final String ROLERESPONSABLEPLANAUDIT = "RESPONSABLE_PLAN_AUDIT";
	public static final String ROLEPILOTEPROCESSUS = "PILOTE_PROCESSUS";
	public static final String ROLEMANAGEMENTOPERATIONNEL = "MANAGEMENT_OPERATIONNEL";
	public static final String ROLEREFERENTECME = "REFERENT_ECME";
	public static final String ROLEMEMBREACTIVITE = "MEMBRE_ACTIVITE";
	public static final String ROLERESPONSABLEACTION = "RESPONSABLE_ACTION";
	public static final String ROLERESPONSABLESYSTEMEMANAGEMENT = "RESPONSABLE_SYSTEME_MANAGEMENT";

}
