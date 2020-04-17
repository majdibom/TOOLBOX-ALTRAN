package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Project;

/**
 * Represents repository of project
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
