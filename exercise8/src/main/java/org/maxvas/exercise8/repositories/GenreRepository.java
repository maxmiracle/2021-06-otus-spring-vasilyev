package org.maxvas.exercise8.repositories;

import org.maxvas.exercise8.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Genre findByName(String name);

    List<Genre> findAll();

    Optional<Genre> findById(UUID id);

    void deleteById(UUID id);
}
