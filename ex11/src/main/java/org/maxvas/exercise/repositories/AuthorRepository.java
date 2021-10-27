package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
    Mono<Author> findByName(String name);

    Flux<Author> findAll();

    Mono<Author> findById(UUID id);

    Mono<Void> deleteById(UUID id);
}
