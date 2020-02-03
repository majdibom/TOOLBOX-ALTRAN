package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import com.altran.toolsbox.qualitymanagement.model.DeliveryModel;

/**
 * Represents the interface of delivery model service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface DeliveryModelService {

	List<DeliveryModel> findAll();

}
