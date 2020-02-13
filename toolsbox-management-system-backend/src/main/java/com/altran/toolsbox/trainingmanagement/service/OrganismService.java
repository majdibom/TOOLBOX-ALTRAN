package com.altran.toolsbox.trainingmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.trainingmanagement.model.Organism;

/**
 * Represents the interface of organism service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface OrganismService {

	List<Organism> findAll();

	Page<Organism> findAllByPage(Pageable pageable);

	Organism findById(Long id);

	Organism create(Organism organism);

	Organism update(Organism organism, Long id);

	void delete(Long id);

	Page<Organism> simpleSearch(String term, Pageable pageable);

}
