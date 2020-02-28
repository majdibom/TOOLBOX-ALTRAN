package com.altran.toolsbox.qualitymanagement.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.ActionStatus;
import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.qualitymanagement.model.Origin;
import com.altran.toolsbox.qualitymanagement.model.Priority;
import com.altran.toolsbox.qualitymanagement.model.TypeAction;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of action
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

	Page<Action> findByResponsibleAction(User user, Pageable pageable);

	List<Action> findByGap(Gap gap);
	
	@Query("SELECT t FROM Action t where LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.actionStatus) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.typeAction) LIKE CONCAT('%', LOWER ( :term ), '%')OR LOWER (t.origin) LIKE CONCAT('%', LOWER ( :term ), '%')")
	public Page<Action> simpleSearch(@Param("term") String term, Pageable pageable);

	@Query("SELECT DISTINCT t FROM Action t where ((t.actionStatus in (:status))"
			+ " OR ((:status) is null)) AND ((t.priority in (:priorities))"
			+ " OR ((:priorities) is null))  AND ((t.typeAction in (:types))"
			+ " OR ((:types) is null)) AND ((t.origin in (:origins))"
			+ " OR ((:origins) is null)) ")
	public Page<Action> advancedSearch(@Param("status") Set<ActionStatus> Status,
			@Param("priorities") Set<Priority> priorities, @Param("types") Set<TypeAction> Types,
			@Param("origins") Set<Origin> origins, Pageable pageable);
}
