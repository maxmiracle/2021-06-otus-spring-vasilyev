package org.maxvas.exercise5.dao;

import org.maxvas.exercise5.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreDao {
    int count();

    UUID insert(Genre author);

    void update(Genre author);

    List<Genre> getAll();

    Optional<Genre> getById(UUID id);

    Optional<Genre> getByName(String name);

    void deleteById(UUID id);
}
