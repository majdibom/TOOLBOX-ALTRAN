package com.altran.toolsbox.trainingmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.trainingmanagement.model.Training;

/**
 * Represents the interface of training service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface TrainingService {

	List<Training> findAll();

	Page<Training> findAllByPage(Pageable pageable);

	Training findById(Long id);

	Training create(Training training);

	Training update(Training training, Long id);

	void delete(Long id);

	Page<Training> simpleSearch(String term, Pageable pageable);

}
