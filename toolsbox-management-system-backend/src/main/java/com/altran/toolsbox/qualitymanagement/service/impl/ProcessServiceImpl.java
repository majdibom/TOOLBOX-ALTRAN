package com.altran.toolsbox.qualitymanagement.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Process;
import com.altran.toolsbox.qualitymanagement.repository.ProcessRepository;
import com.altran.toolsbox.qualitymanagement.service.ProcessService;

import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

/**
 * Represents implementation of process service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class ProcessServiceImpl implements ProcessService {

	private final ProcessRepository processRepository;

	/**
	 * Constructor of ProcessServiceImpl
	 * 
	 * @param processRepository the repository of process
	 */
	@Autowired
	public ProcessServiceImpl(ProcessRepository processRepository) {
		this.processRepository = processRepository;
	}

	/**
	 * Gets the list of all process
	 * 
	 * @return list of all process
	 */
	@Override
	public List<Process> findAll() {
		return processRepository.findAll();
	}

	/**
	 * gets one process by his id
	 * 
	 * @param id the id of the process
	 * @return process object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Process findById(Long id) {
		Optional<Process> process = processRepository.findById(id);
		if (process.isPresent())
			return process.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

}
