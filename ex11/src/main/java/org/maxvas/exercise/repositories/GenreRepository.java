package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {
    Mono<Genre> findByName(String name);

    Flux<Genre> findAll();

    Mono<Genre> findById(UUID id);

    Mono<Void> deleteById(UUID id);
}
