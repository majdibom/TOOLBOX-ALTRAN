package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Exposure;
import com.altran.toolsbox.qualitymanagement.repository.ExposureRepository;
import com.altran.toolsbox.qualitymanagement.service.ExposureService;

@Service
public class ExposureServiceImpl implements ExposureService {

	private final ExposureRepository exposureRepository;

	/**
	 * Constructor of ExposureServiceImpl
	 * 
	 * @param actionRepository
	 *            the repository of action
	 * 
	 */
	@Autowired
	public ExposureServiceImpl(ExposureRepository exposureRepository) {
		super();
		this.exposureRepository = exposureRepository;
	}

	/**
	 * Creates a new exposure
	 * 
	 * @param exposure
	 *            the exposure to create
	 * @return the created exposure
	 * @throws EntityExistsException
	 *             if there is already existing entity with such ID
	 */
	@Override
	public Exposure create(Exposure exposure) {
		if (exposure.getId() != null && exposureRepository.existsById(exposure.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return exposureRepository.save(exposure);
	}

}
