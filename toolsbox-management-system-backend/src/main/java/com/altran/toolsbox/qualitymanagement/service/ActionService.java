package com.altran.toolsbox.qualitymanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.toolsbox.qualitymanagement.model.Action;
import com.altran.toolsbox.qualitymanagement.model.searchfilter.ActionFilter;

/**
 * Represents the interface of action service
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
public interface ActionService {

	List<Action> findAll();

	Page<Action> findAllByPage(Pageable pageable);

	Action findById(Long id);

	List<Action> findByGap(Long id);

	Page<Action> findByResponsibleAction(String username, Pageable pageable);

	Page<Action> simpleSearch(String term, Pageable pageable);

	Action create(Action action);

	Action update(Action action, Long id);

	Boolean delete(Long id);

	Page<Action> advancedSearch(ActionFilter actionFilter, Pageable pageable);

}
