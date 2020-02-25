package com.altran.toolsbox.qualitymanagement.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.RiskAction;
import com.altran.toolsbox.qualitymanagement.model.RiskActionId;

/**
 * Represents repository of RiskAction
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */

@Repository
public interface RiskActionRepository extends JpaRepository<RiskAction, RiskActionId> {

	 Set<RiskAction> findByAction(Action action);

}
