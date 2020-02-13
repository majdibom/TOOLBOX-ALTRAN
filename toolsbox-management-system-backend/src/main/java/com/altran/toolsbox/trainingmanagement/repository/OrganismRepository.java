package com.altran.toolsbox.trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.trainingmanagement.model.Organism;

/**
 * Represents repository of organism
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface OrganismRepository extends JpaRepository<Organism, Long> {

	@Query("SELECT t FROM Organism t where LOWER (t.name) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.planning) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Organism> simpleSearch(@Param("term") String term, Pageable pageable);
}
