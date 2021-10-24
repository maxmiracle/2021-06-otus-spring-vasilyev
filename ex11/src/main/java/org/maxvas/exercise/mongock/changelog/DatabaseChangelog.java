package org.maxvas.exercise.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.maxvas.exercise.domain.Author;
import org.maxvas.exercise.domain.Book;
import org.maxvas.exercise.domain.Genre;
import org.maxvas.exercise.repositories.AuthorRepository;
import org.maxvas.exercise.repositories.BookRepository;
import org.maxvas.exercise.repositories.GenreRepository;

import java.util.UUID;

@ChangeLog
public class DatabaseChangelog {

    Author authorKandinsky = new Author(UUID.fromString("87eb997c-d886-4561-8bac-372d48886359"), "Василий Кандинский");
    Author authorWebb = new Author(UUID.fromString("a768a964-45fd-4161-bbcd-2c35819c86fa"), "Frank Webb");
    Genre genreMonography = new Genre(UUID.fromString("825bcc67-b8d2-44a9-86f3-e9497584ef7e"), "Monography");

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
        bookRepository.save(new Book(UUID.fromString("834e69d4-2567-446a-af42-93346bf4c8a3"), "Точка и Линия", authorKandinsky, genreMonography));
        bookRepository.save(new Book(UUID.fromString("e6aac080-9242-41a2-b62c-a0bbffa6d165"), "Dynamic Composition", authorWebb, genreMonography));
    }

}
