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
	public static final String OPENDATE = "openDate";
	public static final String EFFMEASCRITERION = "effMeasCriterion";
	public static final String PERFORMANCECRITERIA = "performanceCriteria";
	public static final String EFFICIENCYCRITERIA = "efficiencyCriteria";
	public static final String DUEDATE = "dueDate";
	public static final String UPDATEDDUEDATE = "updatedDueDate";
	public static final String COMMENTS = "comments";
	public static final String EFFMESDATE = "effMesDate";
	public static final String RESPONSIBLEACTION = "responsibleAction";
	public static final String GAP = "gap";
	public static final String ACTIONSTATUS = "actionStatus";
	public static final String PRIORITY = "priority";
	public static final String TYPEACTION = "typeAction";
	public static final String ORIGIN = "origin";
	public static final String REALISATIONDATE = "realisationDate";

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
	public static final String TYPEGAP = "typeGap";

	// Origin
	public static final String Risk = "Risk";
	// Project
	public static final String DELIVERYMODEL = "deliveryModel";

	// Week
	public static final String NUMBER = "number";
	// Action Status
	public static final String OPEN = "OPEN";
	public static final String DONE = "DONE";
	public static final String BLOCKED = "BLOCKED";
	public static final String DELAYED = "DELAYED";
	public static final String CANCELLED = "CANCELLED";
	public static final String CLOSED = "CLOSED";
	// Risk
	public static final String PROBABILITY = "probability";
	public static final String SEVERITY = "severity";
	public static final String EXPOSURE = "exposure";
	public static final String RISKSTATUS = "riskStatus";
	public static final String DETECTIONDATE = "detectionDate";
	public static final String CLOSUREDATE = "closureDate";
	public static final String RISKSTRATEGY = "riskStrategy";
	public static final String ACTION = "Action";
	public static final String CONTINGENCYPLAN = "contingencyPlan";
	public static final String MITIGATIONAPPROACH = "mitigationApproach";
	public static final String RISKNATURE = "riskNature";
	public static final String RISKPRIORITY = "riskPriority";
	public static final String RISKPILOTE = "riskPilote";
	public static final String IMPACT = "impact";
	public static final String IDENTOFFACTORS = "Identoffactors";
	public static final String EXPOSUREVALUE = "exposureValue";
	public static final String EXPOSURES = "exposures";

	// Probability
	public static final String Faible = "Faible";
	public static final String Moyenne = "Moyenne";
	public static final String Très_probable = "Très_probable";
	public static final String Presque_sur = "Presque_sur";

	// Severity
	public static final String Mineur = "Mineur";
	public static final String Moyen = "Moyen";
	public static final String Important = "Important";
	public static final String Trés_Important = "Trés_Important";

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
