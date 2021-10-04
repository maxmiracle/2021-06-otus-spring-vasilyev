package org.maxvas.exercise8.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.maxvas.exercise8.domain.Author;
import org.maxvas.exercise8.domain.Book;
import org.maxvas.exercise8.domain.Genre;
import org.maxvas.exercise8.repositories.AuthorRepository;
import org.maxvas.exercise8.repositories.BookRepository;
import org.maxvas.exercise8.repositories.GenreRepository;

import java.util.UUID;

@ChangeLog
public class DatabaseChangelog {

    Author authorKandinsky = new Author(UUID.randomUUID(), "Василий Кандинский");
    Author authorWebb = new Author(UUID.randomUUID(), "Frank Webb");
    Genre genreMonography = new Genre(UUID.randomUUID(), "Monography");

    @ChangeSet(order = "001", id = "dropDb", author = "maxvas", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "maxvas")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(authorKandinsky);
        authorRepository.save(authorWebb);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "maxvas")
    public void insertGenres(GenreRepository gernreRepository) {
        gernreRepository.save(genreMonography);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "maxvas")
    public void insertBooks(BookRepository bookRepository) {
        bookRepository.save(new Book(UUID.randomUUID(), "Точка и Линия", authorKandinsky, genreMonography));
        bookRepository.save(new Book(UUID.randomUUID(), "Dynamic Composition", authorWebb, genreMonography));
    }

}
