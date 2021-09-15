package org.maxvas.exercise6.repositories;

import org.maxvas.exercise6.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Long count();
    Author save(Author author);
    Optional<Author> findOne(UUID id);
    Optional<Author> findOneByName(String name);
    List<Author> findAll();
    void delete(Author author);
}
