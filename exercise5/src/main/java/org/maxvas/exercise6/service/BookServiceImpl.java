package org.maxvas.exercise6.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.domain.Genre;
import org.maxvas.exercise6.repositories.AuthorRepository;
import org.maxvas.exercise6.repositories.BookRepository;
import org.maxvas.exercise6.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public Book createBook(String title, String authorName, String genreName) {
        Genre genre = findGenreByName(genreName).orElseGet(()->newGenre(genreName));
        Author author = findAuthorByName(authorName).orElseGet(()->newAuthor(authorName));
        return bookRepository.save(new Book(null, title, author, genre));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findGenreByName(String name) {
        var genre = genreRepository.findOneByName(name);
        return genre;
    }

    @Transactional
    @Override
    public Genre newGenre(String name) {
            Genre newGenre = new Genre(null, name);
            Genre genre = genreRepository.save(newGenre);
            return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findAuthorByName(String name) {
        var author = authorRepository.findOneByName(name);
        return author;
    }

    @Transactional
    @Override
    public Author newAuthor(String name) {
            Author newAuthor = new Author(null, name);
            Author author = authorRepository.save(newAuthor);
            return author;
    }

    @Transactional
    @Override
    public void update(UUID id, String title, String authorName, String genreName) {
        Book updatedBook = bookRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error update book with unknown id"));
        Genre genre = findGenreByName(genreName).orElseGet(()->newGenre(genreName));
        Author author = findAuthorByName(authorName).orElseGet(()->newAuthor(authorName));
        updatedBook.setTitle(title);
        updatedBook.setAuthor(author);
        updatedBook.setGenre(genre);
        bookRepository.save(updatedBook);
    }

    @Transactional(readOnly = true)
    @Override
    public String allBooksInfo() {
        return bookRepository.findAll().stream()
                .map(book -> ToStringBuilder.reflectionToString(book, ToStringStyle.NO_CLASS_NAME_STYLE))
                .collect(Collectors.joining(",\n"));
    }

    @Transactional(readOnly = true)
    @Override
    public String bookInfo(UUID id) {
        Optional<Book> book = bookRepository.findOne(id);
        if (book.isPresent()) {
            return ToStringBuilder.reflectionToString(book.get(), ToStringStyle.NO_CLASS_NAME_STYLE);
        } else {
            return "Book not found";
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }
}