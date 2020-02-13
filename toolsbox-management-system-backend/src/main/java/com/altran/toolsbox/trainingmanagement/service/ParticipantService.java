package com.altran.toolsbox.trainingmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.trainingmanagement.model.Participant;

/**
 * Represents the interface of participant service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface ParticipantService {

	List<Participant> findAll();

	Page<Participant> findAllByPage(Pageable pageable);

	Participant findById(Long id);

	Participant create(Participant participant);

	Participant update(Participant participant, Long id);

	void delete(Long id);

	Page<Participant> simpleSearch(String term, Pageable pageable);

}
