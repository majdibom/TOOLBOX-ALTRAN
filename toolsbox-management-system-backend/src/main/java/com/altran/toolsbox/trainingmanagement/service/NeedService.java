package com.altran.toolsbox.trainingmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.trainingmanagement.model.Need;

/**
 * Represents the interface of need service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface NeedService {

	List<Need> findAll();

	Page<Need> findAllByPage(Pageable pageable);

	Need findById(Long id);

	Need create(Need need);

	Need update(Need need, Long id);

	void delete(Long id);

	Page<Need> simpleSearch(String term, Pageable pageable);

}
