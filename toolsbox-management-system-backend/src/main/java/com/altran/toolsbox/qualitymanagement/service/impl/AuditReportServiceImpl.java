package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.altran.toolsbox.qualitymanagement.model.AuditReport;
import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.qualitymanagement.repository.AuditReportRepository;
import com.altran.toolsbox.qualitymanagement.service.AuditReportService;
import com.altran.toolsbox.qualitymanagement.service.AuditService;
import com.altran.toolsbox.qualitymanagement.service.GapService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;

/**
 * Represents implementation of audit report service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class AuditReportServiceImpl implements AuditReportService {

	private final AuditReportRepository auditReportRepository;

	private GapService gapService;

	private UserService userService;

	private AuditService auditService;

	/**
	 * Constructor of AuditReportServiceImp
	 * 
	 * @param auditReportRepository
	 *            the repository of audit report
	 */
	@Autowired
	public AuditReportServiceImpl(AuditReportRepository auditReportRepository) {
		super();
		this.auditReportRepository = auditReportRepository;
	}

	/**
	 * Changes action service.
	 * 
	 * @param actionService
	 *            action service.
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes gap service.
	 * 
	 * @param gapServiceImp
	 *            gap service.
	 */
	@Autowired
	public void setGapService(GapService gapService) {
		this.gapService = gapService;
	}

	/**
	 * Changes audit service.
	 * 
	 * @param auditServiceImp
	 *            audit service.
	 */
	@Autowired
	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	/**
	 * Gets the list of all audit reports
	 * 
	 * @return list of all audit reports
	 */
	@Override
	public List<AuditReport> findAll() {
		return auditReportRepository.findAll();
	}

	/**
	 * Gets the list of all audit report by their responsible
	 * 
	 * @param username
	 *            audit responsible userName
	 * @return list of all audit report by their responsible
	 */
	@Override
	public Page<AuditReport> findByResponsableAudit(String username, Pageable pageable) {
		User responsableAudite = userService.findByUsername(username).get();
		return auditReportRepository.findByAuditor(responsableAudite, pageable);
	}

	@Override
	public AuditReport findById(Long id) {
		Optional<AuditReport> auditReport = auditReportRepository.findById(id);
		if (auditReport.isPresent())
			return auditReport.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new risk
	 * 
	 * @param risk
	 *            the risk to create
	 * @return the created risk
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public AuditReport create(AuditReport auditReport) {
		if (auditReport.getId() != null && auditReportRepository.existsById(auditReport.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		// Get gap list of the created report
		Set<Gap> gaps = auditReport.getGaps();
		// Empty gap list in created report
		auditReport.setGaps(new HashSet<Gap>());
		// new gap list to assign to the created risk
		Set<Gap> createdGaps = new HashSet<>();
		AuditReport createdAuditReport = auditReportRepository.save(auditReport);

		// Save every gap in database and add it to report gap list
		for (Gap gap : gaps) {
			Gap createdGap = gapService.create(gap);
			createdGap.setAuditReport(createdAuditReport);
			createdGaps.add(createdGap);
		}
		// Assign the new gap list to the created report
		createdAuditReport.setGaps(createdGaps);
		return auditReportRepository.save(createdAuditReport);
	}

	/**
	 * Updates one audit report
	 * 
	 * @param id
	 *            the id of the audit report
	 * @param auditReport
	 *            the new audit report object with the new values
	 * @return the updated audit report
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public AuditReport update(AuditReport auditReport, Long id) {
		if (id != null && !auditReportRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		AuditReport oldReport = findById(id);
		Set<Gap> gaps = oldReport.getGaps();
		auditReport.setGaps(gaps);
		auditReport.setId(id);
		return auditReportRepository.save(auditReport);
	}

	@Override
	public void validateReport(String validation, String validator, Long id) {
		try {
			AuditReport report = findById(id);
			Date date = java.sql.Date.valueOf(LocalDate.now());
			if (validator.equals("auditor")) {
				report.setValidationAuditor(validation);
				report.setValidationAuditorDate(date);
			} else {
				report.setValidationAudited(validation);
				report.setValidationAuditedDate(date);
			}
			auditReportRepository.save(report);
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Deletes one audit report
	 * 
	 * @param id
	 *            the of the deleted audit report
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !auditReportRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		AuditReport auditReport = auditReportRepository.findById(id).get();
		Audit audit = auditReport.getAudit();
		audit.setAuditReport(null);
		auditService.update(audit, audit.getId());
		auditReportRepository.deleteById(id);
	}

}
