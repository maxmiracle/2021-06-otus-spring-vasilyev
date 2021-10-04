package org.maxvas.repositories;

import org.maxvas.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author findByName(String name);
}
