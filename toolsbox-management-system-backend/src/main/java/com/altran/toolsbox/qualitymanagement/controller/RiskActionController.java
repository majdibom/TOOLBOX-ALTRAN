package com.altran.toolsbox.qualitymanagement.controller;

import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_CREATED;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.RISK_NOT_CREATED;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.toolsbox.qualitymanagement.model.RiskAction;
import com.altran.toolsbox.qualitymanagement.service.RiskActionService;
import com.altran.toolsbox.util.GenericResponse;
import com.altran.toolsbox.util.Translator;

/**
 * Represents Rest controller of RiskAction
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/risk-action")
public class RiskActionController {

	private RiskActionService riskActionService;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of ActionController
	 * 
	 * @param actionService
	 *            the service of action
	 */
	@Autowired
	public RiskActionController(RiskActionService riskActionService) {
		this.riskActionService = riskActionService;
	}

	/**
	 * Changes message response object for sending message
	 * 
	 * @param messageResponse
	 *            generic response with string as object.
	 */
	@Autowired
	public void setMessageResponse(GenericResponse<String> messageResponse) {
		this.messageResponse = messageResponse;
	}

	/**
	 * Creates a new risk action relationship
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param riskAction
	 *            the risk action to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         risk with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createRiskAction(@RequestBody RiskAction riskAction) {
		try {
			riskActionService.create(riskAction);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RISK_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			System.err.println(e);
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RISK_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

}
