package com.altran.toolsbox.qualitymanagement.service.impl;

import static com.altran.toolsbox.util.constant.ResponseConstants.ENTITY_EXIST;
import static com.altran.toolsbox.util.constant.ResponseConstants.NO_ENTITY_DB;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.toolsbox.qualitymanagement.model.Comment;
import com.altran.toolsbox.qualitymanagement.repository.CommentRepository;
import com.altran.toolsbox.qualitymanagement.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	/**
	 * Constructor of ActionServiceImp
	 * 
	 * @param actionRepository the repository of action
	 * 
	 */
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	/**
	 * Creates a new comment
	 * 
	 * @param comment the comment to create
	 * @return the created comment
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Comment create(Comment comment) {
		if (comment.getId() != null && commentRepository.existsById(comment.getId())) {
			throw new EntityExistsException(ENTITY_EXIST);
		}
		return commentRepository.save(comment);
	}

	/**
	 * Check if comment exist
	 * 
	 * @param id the of the comment
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public boolean existsById(Long id) {
		if (commentRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	/**
	 * Deletes one comment
	 * 
	 * @param id the of the deleted comment
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !commentRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		commentRepository.deleteById(id);
	}

}
