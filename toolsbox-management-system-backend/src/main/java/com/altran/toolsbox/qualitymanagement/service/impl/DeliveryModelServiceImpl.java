package com.altran.toolsbox.qualitymanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.DeliveryModel;
import com.altran.toolsbox.qualitymanagement.repository.DeliveryModelRepository;
import com.altran.toolsbox.qualitymanagement.service.DeliveryModelService;

/**
 * Represents implementation of delivery model service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Service
public class DeliveryModelServiceImpl implements DeliveryModelService {

	private final DeliveryModelRepository deliveryModelRepository;

	/**
	 * Constructor of DeliveryModelRepository
	 * 
	 * @param deliveryModelRepository the repository of delivery model
	 */
	@Autowired
	public DeliveryModelServiceImpl(DeliveryModelRepository deliveryModelRepository) {
		super();
		this.deliveryModelRepository = deliveryModelRepository;
	}

	/**
	 * Gets the list of all delivery models
	 * 
	 * @return list of all delivery models
	 */
	@Override
	public List<DeliveryModel> findAll() {
		return deliveryModelRepository.findAll();
	}

}
