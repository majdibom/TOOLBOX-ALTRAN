package com.altran.toolsbox.qualitymanagement.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Audit;
import com.altran.toolsbox.qualitymanagement.repository.AuditRepository;
import com.altran.toolsbox.qualitymanagement.service.AuditService;
import com.altran.toolsbox.usermanagement.model.User;
import com.altran.toolsbox.usermanagement.service.UserService;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;
import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;

@Service
public class AuditServiceImpl implements AuditService {

	private final AuditRepository auditRepository;

	private UserService userService;

	/**
	 * Constructor of AuditServiceImp
	 * 
	 * @param auditRepository
	 *            the repository of audit
	 * 
	 */
	@Autowired
	public AuditServiceImpl(AuditRepository auditRepository) {
		this.auditRepository = auditRepository;
	}

	/**
	 * Changes user service.
	 * 
	 * @param userService
	 *            user service.
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Gets the list of all audits
	 * 
	 * @return list of all audits
	 */
	@Override
	public List<Audit> findAll() {
		return auditRepository.findAll();
	}

	/**
	 * Gets the list of all audits by page
	 * 
	 * @return list of all audits by page
	 */
	@Override
	public Page<Audit> findAllByPage(Pageable pageable) {
		return auditRepository.findAll(pageable);
	}

	/**
	 * Gets the list of all audits of the principal auditor
	 * 
	 * @param username
	 *            the principal auditor's user name.
	 * @return list of all audits of the principal auditor
	 */
	@Override
	public Page<Audit> findByPrimaryAuditor(String username, Pageable pageable) {
		User auditor = userService.findByUsername(username).get();
		return auditRepository.findByPrimaryAuditor(auditor, pageable);

	}

	/**
	 * Gets the list of all audits of the accompanying auditor
	 * 
	 * @param username
	 *            the accompanying auditor's user name.
	 * @return list of all audits of the accompanying auditor
	 */
	@Override
	public Page<Audit> findByAccompanyingAuditor(String username, Pageable pageable) {
		User auditor = userService.findByUsername(username).get();
		return auditRepository.findByAccompanyingAuditor(auditor, pageable);
	}

	/**
	 * Gets the list of all audits of the audited
	 * 
	 * @param username
	 *            the audited's user name.
	 * @return list of all audits of the audited
	 */
	@Override
	public Page<Audit> findByAudited(String username, Pageable pageable) {
		User audited = userService.findByUsername(username).get();
		return auditRepository.findByAudited(audited, pageable);
	}

	/**
	 * gets one audit by his id
	 * 
	 * @param id
	 *            the id of the audit
	 * @return audit object with the same id
	 * @throws NoSuchElementException
	 *             if no element is present with such ID
	 */
	@Override
	public Audit findById(Long id) {
		Optional<Audit> audit = auditRepository.findById(id);
		if (audit.isPresent())
			return audit.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new audit
	 * 
	 * @param audit
	 *            the audit to create
	 * @return the created audit
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public Audit create(Audit audit) {
		if (audit.getId() != null && auditRepository.existsById(audit.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return auditRepository.save(audit);
	}

	/**
	 * Updates one audit
	 * 
	 * @param id
	 *            the id of the audit
	 * @param audit
	 *            the new audit object with the new values
	 * @return the updated audit
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public Audit update(Audit audit, Long id) {
		if (id != null && !auditRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		audit.setId(id);
		return auditRepository.save(audit);
	}

	/**
	 * Deletes one action
	 * 
	 * @param id
	 *            the of the deleted action
	 * @throws EntityNotFoundException
	 *             if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !auditRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		auditRepository.deleteById(id);
	}

}
