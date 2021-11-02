package org.maxvas.repositories;

import org.maxvas.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;



public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
