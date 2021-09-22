package org.maxvas.exercise6.repositories;

import org.maxvas.exercise6.domain.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Long count();
    Comment save(Comment comment);
    Optional<Comment> findOne(UUID id);
    List<Comment> findAllByBookId(UUID bookId);
    List<Comment> findAll();
    void delete(Comment comment);    
}
