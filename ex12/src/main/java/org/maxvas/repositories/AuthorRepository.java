package org.maxvas.repositories;

import org.maxvas.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
