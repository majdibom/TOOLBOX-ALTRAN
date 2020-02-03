package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Week;
import com.altran.toolsbox.qualitymanagement.repository.WeekRepository;
import com.altran.toolsbox.qualitymanagement.service.WeekService;

/**
 * Represents implementation of week service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class WeekServiceImpl implements WeekService {

	private final WeekRepository weekRepository;

	/**
	 * Constructor of ProjectServiceImpl
	 * 
	 * @param projectRepository the repository of gap
	 */
	@Autowired
	public WeekServiceImpl(WeekRepository weekRepository) {
		this.weekRepository = weekRepository;
	}

	/**
	 * Gets the list of all weeks
	 * 
	 * @return list of all weeks
	 */
	@Override
	public List<Week> findAll() {
		return weekRepository.findAll();
	}

	@Override
	public Week findById(Long id) {
		Optional<Week> week = weekRepository.findById(id);
		if (week.isPresent())
			return week.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

}
