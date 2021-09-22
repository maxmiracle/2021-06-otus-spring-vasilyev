package org.maxvas.exercise6.repositories;

import org.maxvas.exercise6.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Long count();
    Book save(Book book);
    Optional<Book> findOne(UUID id);
    Optional<Book> findOneByTitle(String title);
    List<Book> findAll();
    void delete(Book book);
    void deleteById(UUID id);
}
