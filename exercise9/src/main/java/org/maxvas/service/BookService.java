package org.maxvas.service;

import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Book createBook(String title, String authorName, String genreName);

    Optional<Genre> findGenreByName(String name);

    Genre newGenre(String name);

    Optional<Author> findAuthorByName(String name);

    Author newAuthor(String name);

    void update(UUID id, String title, String authorName, String genreName);

    String allBooksInfo();

    String bookInfo(UUID id);

    Optional<Book> findById(UUID id);

    void deleteById(UUID id);

    List<Book> findAll();
}
