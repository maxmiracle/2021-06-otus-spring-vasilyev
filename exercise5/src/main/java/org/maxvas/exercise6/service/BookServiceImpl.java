package org.maxvas.exercise6.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.maxvas.exercise6.dao.AuthorDao;
import org.maxvas.exercise6.dao.BookDao;
import org.maxvas.exercise6.dao.GenreDao;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final GenreDao genreDao;

    private final AuthorDao authorDao;

    @Override
    public UUID createBook(String title, String authorName, String genreName) {
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
        return bookDao.insert(new Book(null, title, author, genre));
    }

    @Override
    public Genre getGenreByName(String name) {
        var genre = genreDao.getByName(name);
        if (genre.isEmpty()) {
            Genre newGenre = new Genre(null, name);
            UUID id = genreDao.insert(newGenre);
            newGenre.setId(id);
            return newGenre;
        } else {
            return genre.get();
        }
    }

    @Override
    public Author getAuthorByName(String name) {
        var author = authorDao.getByName(name);
        if (author.isEmpty()) {
            Author newAuthor = new Author(null, name);
            UUID id = authorDao.insert(newAuthor);
            newAuthor.setId(id);
            return newAuthor;
        } else {
            return author.get();
        }
    }

    @Override
    public void update(UUID id, String title, String authorName, String genreName) {
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
        Book updatedBook = new Book(id, title, author, genre);
        bookDao.update(updatedBook);
    }

    @Override
    public String allBooksInfo() {
        return bookDao.getAll().stream()
                .map(book -> ToStringBuilder.reflectionToString(book, ToStringStyle.NO_CLASS_NAME_STYLE))
                .collect(Collectors.joining(",\n"));
    }

    @Override
    public String bookInfo(UUID id) {
        Optional<Book> book = bookDao.getById(id);
        if (book.isPresent()) {
            return ToStringBuilder.reflectionToString(book.get(), ToStringStyle.NO_CLASS_NAME_STYLE);
        } else {
            return "Book not found";
        }
    }

    @Override
    public void deleteById(UUID id) {
        bookDao.deleteById(id);
    }
}
