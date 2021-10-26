package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Book;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    //special for exercise
    Mono<Book> findByTitle(@Param("title") String title);

    Flux<Book> findAll();

    Mono<Book> findById(UUID id);

    Mono<Void> deleteById(UUID id);
}
