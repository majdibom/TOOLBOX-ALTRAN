package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.FilterConstants.DELIVERYMODEL_FILTER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.DeliveryModel;
import com.altran.toolsbox.qualitymanagement.service.DeliveryModelService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Represents Rest controller of delivery model
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/delivery-model")
public class DeliveryModelController {

	private final DeliveryModelService deliveryModelService;

	/**
	 * Constructor of DeliveryModelController
	 * 
	 * @param deliveryModelService the service of audit report
	 */
	@Autowired
	public DeliveryModelController(DeliveryModelService deliveryModelService) {
		this.deliveryModelService = deliveryModelService;
	}

	/**
	 * Gets the list of all delivery models
	 * 
	 * @return list of all delivery models
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getAllDeliveryModels() {
		List<DeliveryModel> deliveryModelList = deliveryModelService.findAll();
		/** Filtering data to send **/
		// Filter the delivery model object
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(DELIVERYMODEL_FILTER, SimpleBeanPropertyFilter.serializeAll());
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue deliveryModelsMapping = new MappingJacksonValue(deliveryModelList);
		deliveryModelsMapping.setFilters(filterProvider);
		return deliveryModelsMapping;
	}
}
