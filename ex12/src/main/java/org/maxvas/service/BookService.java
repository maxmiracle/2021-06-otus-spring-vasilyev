package org.maxvas.service;

import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;

import java.util.List;
import java.util.Optional;


public interface BookService {
    Book createBook(String title, String authorName, String genreName);

    Optional<Genre> findGenreByName(String name);

    Genre newGenre(String name);

    Optional<Author> findAuthorByName(String name);

    Author newAuthor(String name);

    void update(Long id, String title, String authorName, String genreName);

    String allBooksInfo();

    String bookInfo(Long id);

    Book getById(Long id);

    void deleteById(Long id);

    List<Book> findAll();
}
