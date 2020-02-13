package com.altran.toolsbox.trainingmanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.altran.toolsbox.trainingmanagement.model.Need;
import com.altran.toolsbox.trainingmanagement.repository.NeedRepository;
import com.altran.toolsbox.trainingmanagement.service.NeedService;

/**
 * Represents implementation of need service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class NeedServiceImp implements NeedService {

	private final NeedRepository needRepository;

	/**
	 * Constructor of NeedServiceImpl
	 * 
	 * @param needRepository the repository of need
	 */
	@Autowired
	public NeedServiceImp(NeedRepository needRepository) {
		this.needRepository = needRepository;
	}

	/**
	 * Gets the list of all needs
	 * 
	 * @return list of all needs
	 */
	@Override
	public List<Need> findAll() {
		return needRepository.findAll();
	}

	/**
	 * Gets the list of all needs by page
	 * 
	 * @return list of all needs by page
	 */
	public Page<Need> findAllByPage(Pageable pageable) {
		return needRepository.findAll(pageable);
	}

	/**
	 * gets one need by his id
	 * 
	 * @param id the id of the need
	 * @return need object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Need findById(Long id) {
		Optional<Need> need = needRepository.findById(id);
		if (need.isPresent())
			return need.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new need
	 * 
	 * @param need the need to create
	 * @return the created need
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Need create(Need need) {
		if (need.getId() != null && needRepository.existsById(need.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return needRepository.save(need);
	}

	/**
	 * Updates one need
	 * 
	 * @param id   the id of the need
	 * @param need the new need object with the new values
	 * @return the updated need
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Need update(Need need, Long id) {
		if (id != null && !needRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		need.setId(id);
		return needRepository.save(need);
	}

	/**
	 * Deletes one need
	 * 
	 * @param id the of the deleted need
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !needRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		needRepository.deleteById(id);
	}

	/**
	 * Searches for needs by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of needs contains the input term by page
	 */
	@Override
	public Page<Need> simpleSearch(String term, Pageable pageable) {
		return needRepository.simpleSearch(term, pageable);
	}
}
