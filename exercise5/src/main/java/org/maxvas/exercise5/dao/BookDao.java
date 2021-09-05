package org.maxvas.exercise5.dao;

import org.maxvas.exercise5.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDao {
    int count();

    UUID insert(Book book);

    void update(Book book);

    List<Book> getAll();

    Optional<Book> getById(UUID id);

    void deleteById(UUID id);
}
