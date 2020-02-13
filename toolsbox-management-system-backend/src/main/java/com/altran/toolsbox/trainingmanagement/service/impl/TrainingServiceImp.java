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

import com.altran.toolsbox.trainingmanagement.model.Training;
import com.altran.toolsbox.trainingmanagement.repository.TrainingRepository;
import com.altran.toolsbox.trainingmanagement.service.TrainingService;

/**
 * Represents implementation of training service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class TrainingServiceImp implements TrainingService {

	private TrainingRepository trainingRepository;

	/**
	 * Constructor of TrainingServiceImp
	 * 
	 * @param trainingRepository the repository of training
	 */
	@Autowired
	public TrainingServiceImp(TrainingRepository trainingRepository) {
		this.trainingRepository = trainingRepository;
	}

	/**
	 * Gets the list of all training
	 * 
	 * @return list of all training
	 */
	@Override
	public List<Training> findAll() {
		return trainingRepository.findAll();
	}

	/**
	 * Gets the list of all training by page
	 * 
	 * @return list of all training by page
	 */
	public Page<Training> findAllByPage(Pageable pageable) {
		return trainingRepository.findAll(pageable);
	}

	/**
	 * gets one training by his id
	 * 
	 * @param id the id of the training
	 * @return training object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Training findById(Long id) {
		Optional<Training> training = trainingRepository.findById(id);
		if (training.isPresent())
			return training.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new training
	 * 
	 * @param training the need to create
	 * @return the created training
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Training create(Training training) {
		if (training.getId() != null && trainingRepository.existsById(training.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return trainingRepository.save(training);
	}

	/**
	 * Updates one training
	 * 
	 * @param id       the id of the training
	 * @param training the new training object with the new values
	 * @return the updated training
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Training update(Training training, Long id) {
		if (id != null && !trainingRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		training.setId(id);
		return trainingRepository.save(training);
	}

	/**
	 * Deletes one training
	 * 
	 * @param id the of the deleted training
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !trainingRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		trainingRepository.deleteById(id);
	}

	/**
	 * Searches for training by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of training contains the input term by page
	 */
	@Override
	public Page<Training> simpleSearch(String term, Pageable pageable) {
		return trainingRepository.simpleSearch(term, pageable);
	}
}
