package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findAllByBook_Id(UUID bookId);

    Flux<Comment> findAll();

    Mono<Comment> findById(UUID id);

    Mono<Void> deleteById(UUID id);
}
