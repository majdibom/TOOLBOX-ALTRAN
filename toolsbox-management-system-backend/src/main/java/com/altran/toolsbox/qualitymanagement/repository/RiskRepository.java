package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altran.toolsbox.qualitymanagement.model.Risk;

/**
 * Represents repository of risk
 * 
 * @author Majdi.Ben.Othmen
 * @version 1.0
 */
public interface RiskRepository extends JpaRepository<Risk, Long> {

}
