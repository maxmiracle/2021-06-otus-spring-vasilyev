package org.maxvas.exercise5.dao;

import org.maxvas.exercise5.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorDao {
    int count();

    UUID insert(Author author);

    void update(Author author);

    List<Author> getAll();

    Optional<Author> getById(UUID id);

    Optional<Author> getByName(String name);

    void deleteById(UUID id);
}
