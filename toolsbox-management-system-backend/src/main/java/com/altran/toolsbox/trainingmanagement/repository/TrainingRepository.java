package com.altran.toolsbox.trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.trainingmanagement.model.Training;

/**
 * Represents repository of training
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

	@Query("SELECT t FROM Training t where LOWER (t.trainer) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.status) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Training> simpleSearch(@Param("term") String term, Pageable pageable);
}
