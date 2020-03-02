package com.altran.toolsbox.qualitymanagement.service;

import com.altran.toolsbox.qualitymanagement.model.Comment;

/**
 * Represents the interface of comment service
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public interface CommentService {

	Comment create(Comment comment);

	boolean existsById(Long id);

	void delete(Long id);

}
