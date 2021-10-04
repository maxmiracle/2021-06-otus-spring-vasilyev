package org.maxvas.repositories;

import org.maxvas.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Genre findByName(String name);
}
