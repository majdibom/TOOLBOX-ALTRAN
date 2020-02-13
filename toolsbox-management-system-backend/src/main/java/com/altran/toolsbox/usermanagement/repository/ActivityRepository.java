package com.altran.toolsbox.usermanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.usermanagement.model.Activity;

/**
 * Represents repository of activity
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	List<Activity> findAllByOrderByTitleAsc();

	Page<Activity> findAllByOrderByTitleAsc(Pageable pageable);

	Activity findByTitle(String title);

	@Query("SELECT t FROM Activity t where LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Activity> simpleSearch(@Param("term") String term, Pageable pageable);
}
