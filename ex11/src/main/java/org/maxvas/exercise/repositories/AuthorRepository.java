package org.maxvas.exercise.repositories;

import org.maxvas.exercise.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, String> {
    Author findByName(String name);

    List<Author> findAll();

    Optional<Author> findById(UUID id);

    void deleteById(UUID id);
}
