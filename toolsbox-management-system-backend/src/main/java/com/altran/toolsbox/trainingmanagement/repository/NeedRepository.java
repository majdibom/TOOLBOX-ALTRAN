package com.altran.toolsbox.trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.trainingmanagement.model.Need;

/**
 * Represents repository of need
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {

	@Query("SELECT t FROM Need t where LOWER (t.object) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.category) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Need> simpleSearch(@Param("term") String term, Pageable pageable);
}
