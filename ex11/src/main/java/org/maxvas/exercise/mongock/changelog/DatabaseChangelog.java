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

    public static final String titleDotAndLine = "Точка и Линия";
    public static final UUID idDotAndLine = UUID.fromString("834e69d4-2567-446a-af42-93346bf4c8a3");
    public static final Author authorKandinsky = new Author(UUID.fromString("87eb997c-d886-4561-8bac-372d48886359"), "Василий Кандинский");
    public static final Author authorWebb = new Author(UUID.fromString("a768a964-45fd-4161-bbcd-2c35819c86fa"), "Frank Webb");
    public static final Genre genreMonography = new Genre(UUID.fromString("825bcc67-b8d2-44a9-86f3-e9497584ef7e"), "Monography");
    public static final Genre genreSciFi = new Genre(UUID.fromString("a15bcc67-b8d2-44a9-86f3-e9497584ef00"), "Science Fiction");
    public static final Book dotAndLineBook = new Book(idDotAndLine, titleDotAndLine, authorKandinsky, genreMonography);
    public static final Book dynamicComposition = new Book(UUID.fromString("e6aac080-9242-41a2-b62c-a0bbffa6d165"), "Dynamic Composition", authorWebb, genreMonography);

    @ChangeSet(order = "001", id = "dropDb", author = "maxvas", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "maxvas")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(authorKandinsky).subscribe();
        authorRepository.save(authorWebb).subscribe();
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "maxvas")
    public void insertGenres(GenreRepository gernreRepository) {
        gernreRepository.save(genreMonography).subscribe();
        gernreRepository.save(genreSciFi).subscribe();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "maxvas")
    public void insertBooks(BookRepository bookRepository) {
        bookRepository.save(dotAndLineBook).subscribe();
        bookRepository.save(dynamicComposition).subscribe();
    }

}
