package org.maxvas.exercise5.service;

import lombok.AllArgsConstructor;
import org.maxvas.exercise5.dao.AuthorDao;
import org.maxvas.exercise5.dao.BookDao;
import org.maxvas.exercise5.dao.GenreDao;
import org.maxvas.exercise5.domain.Author;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {

    BookDao bookDao;

    GenreDao genreDao;

    AuthorDao authorDao;

    public UUID createBook(String title, String author, String genre) {
        UUID authorId = getAuthorId(author);
        UUID genreId = getGenreId(genre);
        return bookDao.insert(new Book()
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenreId(genreId));
    }

    public UUID getGenreId(String genre) {
        var genreRecord = genreDao.getByName(genre);
        UUID genreId;
        if (genreRecord.isEmpty()) {
            genreId = genreDao.insert((new Genre()).setName(genre));
        } else {
            genreId = genreRecord.get().getId();
        }
        return genreId;
    }

    public UUID getAuthorId(String author) {
        var authorRecord = authorDao.getByName(author);
        UUID genreId;
        if (authorRecord.isEmpty()) {
            genreId = authorDao.insert((new Author()).setName(author));
        } else {
            genreId = authorRecord.get().getId();
        }
        return genreId;
    }

    public void update(UUID id, String title, String author, String genre) {
        UUID authorId = getAuthorId(author);
        UUID genreId = getGenreId(genre);
        Book updatedBook = new Book()
                .setId(id)
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenreId(genreId);
        bookDao.update(updatedBook);
    }
}
