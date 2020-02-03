package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Process;

/**
 * Represents repository of process
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

}
