package org.maxvas.exercise6.repositories;

import org.maxvas.exercise6.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository {
    Long count();
    Genre save(Genre genre);
    Optional<Genre> findOne(UUID id);
    Optional<Genre> findOneByName(String name);
    List<Genre> findAll();
    void delete(Genre genre);
}
