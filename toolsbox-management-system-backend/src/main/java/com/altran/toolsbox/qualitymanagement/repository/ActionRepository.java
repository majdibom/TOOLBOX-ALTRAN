package com.altran.toolsbox.qualitymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.Gap;
import com.altran.toolsbox.usermanagement.model.User;

/**
 * Represents repository of action
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

	Page<Action> findByResponsibleAction(User user, Pageable pageable);

	List<Action> findByGap(Gap gap);

}
