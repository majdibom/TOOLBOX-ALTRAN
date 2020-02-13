package com.altran.toolsbox.trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.trainingmanagement.model.Participant;

/**
 * Represents repository of participant
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

	@Query("SELECT t FROM Participant t where LOWER (t.firstName) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Participant> simpleSearch(@Param("term") String term, Pageable pageable);
}
