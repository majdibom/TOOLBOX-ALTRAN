package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Week;

/**
 * Represents repository of week
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {

}
