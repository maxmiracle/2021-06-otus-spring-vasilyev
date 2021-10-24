package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findAllByBook_Id(UUID bookId);

    List<Comment> findAll();

    Optional<Comment> findById(UUID id);

    void deleteById(UUID id);
}
