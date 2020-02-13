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

import com.altran.toolsbox.trainingmanagement.model.Organism;
import com.altran.toolsbox.trainingmanagement.repository.OrganismRepository;
import com.altran.toolsbox.trainingmanagement.service.OrganismService;

/**
 * Represents implementation of organism service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class OrganismServiceImp implements OrganismService {

	private final OrganismRepository organismRepository;

	/**
	 * Constructor of OrganismServiceImpl
	 * 
	 * @param organismRepository the repository of organism
	 */
	@Autowired
	public OrganismServiceImp(OrganismRepository organismRepository) {
		this.organismRepository = organismRepository;
	}

	/**
	 * Gets the list of all organisms sorted
	 * 
	 * @return list of all organisms sorted
	 */
	@Override
	public List<Organism> findAll() {
		return organismRepository.findAll();
	}

	/**
	 * Gets the list of all organisms by page
	 * 
	 * @return list of all organisms by page
	 */
	public Page<Organism> findAllByPage(Pageable pageable) {
		return organismRepository.findAll(pageable);
	}

	/**
	 * gets one organism by his id
	 * 
	 * @param id the id of the organism
	 * @return organism object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Organism findById(Long id) {
		Optional<Organism> organism = organismRepository.findById(id);
		if (organism.isPresent())
			return organism.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new organism
	 * 
	 * @param organism the organism to create
	 * @return the created organism
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Organism create(Organism organism) {
		if (organism.getId() != null && organismRepository.existsById(organism.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return organismRepository.save(organism);
	}

	/**
	 * Updates one organism
	 * 
	 * @param id       the id of the organism
	 * @param organism the new organism object with the new values
	 * @return the updated organism
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Organism update(Organism organism, Long id) {
		if (id != null && !organismRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		organism.setId(id);
		return organismRepository.save(organism);
	}

	/**
	 * Deletes one organism
	 * 
	 * @param id the of the deleted organism
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !organismRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		organismRepository.deleteById(id);
	}

	/**
	 * Searches for organisms by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of organisms contains the input term by page
	 */
	@Override
	public Page<Organism> simpleSearch(String term, Pageable pageable) {
		return organismRepository.simpleSearch(term, pageable);
	}

}
