package org.maxvas.exercise8.repositories;

import org.maxvas.exercise8.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, String> {
    //special for exercise
    Book findByTitle(@Param("title") String title);

    List<Book> findAll();

    Optional<Book> findById(UUID id);

    void deleteById(UUID id);
}
