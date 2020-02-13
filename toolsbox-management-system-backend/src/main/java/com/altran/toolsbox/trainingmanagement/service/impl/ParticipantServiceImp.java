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

import com.altran.toolsbox.trainingmanagement.model.Participant;
import com.altran.toolsbox.trainingmanagement.repository.ParticipantRepository;
import com.altran.toolsbox.trainingmanagement.service.ParticipantService;

/**
 * Represents implementation of participant service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Service
public class ParticipantServiceImp implements ParticipantService {

	private ParticipantRepository participantRepository;

	/**
	 * Constructor of ParticipantServiceImp
	 * 
	 * @param participantRepository the repository of participant
	 */
	@Autowired
	public ParticipantServiceImp(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	/**
	 * Gets the list of all participants
	 * 
	 * @return list of all participants
	 */
	@Override
	public List<Participant> findAll() {
		return participantRepository.findAll();
	}

	/**
	 * Gets the list of all participants by page
	 * 
	 * @return list of all participants by page
	 */
	public Page<Participant> findAllByPage(Pageable pageable) {
		return participantRepository.findAll(pageable);
	}

	/**
	 * gets one participant by his id
	 * 
	 * @param id the id of the participant
	 * @return participant object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Participant findById(Long id) {
		Optional<Participant> participant = participantRepository.findById(id);
		if (participant.isPresent())
			return participant.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new participant
	 * 
	 * @param participant the participant to create
	 * @return the created participant
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Participant create(Participant participant) {
		if (participant.getId() != null && participantRepository.existsById(participant.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return participantRepository.save(participant);
	}

	/**
	 * Updates one participant
	 * 
	 * @param id          the id of the participant
	 * @param participant the new participant object with the new values
	 * @return the updated participant
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Participant update(Participant participant, Long id) {
		if (id != null && !participantRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		participant.setId(id);
		return participantRepository.save(participant);
	}

	/**
	 * Deletes one participant
	 * 
	 * @param id the of the deleted participant
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !participantRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		participantRepository.deleteById(id);
	}

	/**
	 * Searches for participants by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of participants contains the input term by page
	 */
	@Override
	public Page<Participant> simpleSearch(String term, Pageable pageable) {
		return participantRepository.simpleSearch(term, pageable);
	}
}
