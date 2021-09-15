package org.maxvas.exercise6.service;

import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.domain.Genre;

import java.util.UUID;

public interface BookService {
    Book createBook(String title, String authorName, String genreName);
    Genre getGenreByName(String name);
    Author getAuthorByName(String name);
    void update(UUID id, String title, String authorName, String genreName);
    String allBooksInfo();
    String bookInfo(UUID id);
    void deleteById(UUID id);
}
