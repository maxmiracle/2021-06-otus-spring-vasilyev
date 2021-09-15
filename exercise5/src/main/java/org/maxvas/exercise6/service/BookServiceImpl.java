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
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
        return bookRepository.save(new Book(null, title, author, genre, null));
    }

    @Transactional
    @Override
    public Genre getGenreByName(String name) {
        var genre = genreRepository.findOneByName(name);
        if (genre.isEmpty()) {
            Genre newGenre = new Genre(null, name);
            Genre genre1 = genreRepository.save(newGenre);
            return genre1;
        } else {
            return genre.get();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthorByName(String name) {
        var author = authorRepository.findOneByName(name);
        if (author.isEmpty()) {
            Author newAuthor = new Author(null, name);
            Author author1 = authorRepository.save(newAuthor);
            return author1;
        } else {
            return author.get();
        }
    }

    @Transactional
    @Override
    public void update(UUID id, String title, String authorName, String genreName) {
        Book updatedBook = bookRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error update book with unknown id"));
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
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
