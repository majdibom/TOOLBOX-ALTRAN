package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.AuditReport;
import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.qualitymanagement.repository.GapRepository;
import com.altran.toolsbox.qualitymanagement.service.AuditReportService;
import com.altran.toolsbox.qualitymanagement.service.GapService;

/**
 * Represents implementation of gap service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class GapServiceImpl implements GapService {

	private final GapRepository gapRepository;

	private AuditReportService auditReportService;

	/**
	 * Constructor of GapServiceImp
	 * 
	 * @param gapRepository the repository of gap
	 */
	@Autowired
	public GapServiceImpl(GapRepository gapRepository) {
		this.gapRepository = gapRepository;
	}

	/**
	 * Changes audit report service.
	 * 
	 * @param auditReportService audit report service.
	 */
	@Autowired
	public void setAuditReportService(AuditReportService auditReportService) {
		this.auditReportService = auditReportService;
	}

	/**
	 * Gets the list of all gaps by their audit report
	 * 
	 * @return list of all gaps by their audit report
	 */
	@Override
	public List<Gap> findByAuditReport(Long id) {
		AuditReport report = auditReportService.findById(id);
		return gapRepository.findByAuditReport(report).orElse(new ArrayList<Gap>());
	}

	/**
	 * gets one gap by his id
	 * 
	 * @param id the id of the gap
	 * @return gap object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Gap findById(Long id) {
		Optional<Gap> gap = gapRepository.findById(id);
		if (gap.isPresent())
			return gap.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new gap
	 * 
	 * @param gap the gap to create
	 * @return the created gap
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Gap create(Gap gap) {
		if (gap.getId() != null && gapRepository.existsById(gap.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return gapRepository.save(gap);
	}

	/**
	 * Updates one gap
	 * 
	 * @param id  the id of the gap
	 * @param gab the new gap object with the new values
	 * @return the updated gap
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Gap update(Gap gap, Long id) {
		if (id != null && !gapRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		gap.setId(id);
		return gapRepository.save(gap);
	}

	/**
	 * Deletes one gap
	 * 
	 * @param id the of the deleted gap
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !gapRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		gapRepository.deleteById(id);
	}

}
