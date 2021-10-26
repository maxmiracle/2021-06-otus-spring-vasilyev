package org.maxvas.exercise.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Author;
import org.maxvas.exercise.domain.Book;
import org.maxvas.exercise.domain.Genre;
import org.maxvas.exercise.mongock.changelog.DatabaseChangelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableMongock
@DataMongoTest
class BookRepositoryTests {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    @Test
    void create() {
        String testName = "TestName";
        Author testAuthor = new Author(UUID.randomUUID(), "TestAuthor");
        Genre testGenre = new Genre(UUID.randomUUID(), "TestGenre");
        Book book = new Book(UUID.randomUUID(), testName, testAuthor, testGenre);
        Book savedBook = bookRepository.save(book).block();
        UUID id = savedBook.getId();
        Mono<Book> bookMono = reactiveMongoTemplate.findById(id, Book.class);
        Book bookActual = bookMono.block();
        assertEquals(book, bookActual);
    }


    @Test
    void findByTitle() {
        Book book = bookRepository.findByTitle(DatabaseChangelog.dynamicComposition.getTitle()).block();
        assertEquals(DatabaseChangelog.dynamicComposition, book);
    }

    @Test
    void update() {
        Book book = bookRepository.findById(DatabaseChangelog.dotAndLineBook.getId()).block();
        Book newBook = book.setTitle("СпрингTest");
        bookRepository.save(newBook).subscribe();
        Book actualBook = bookRepository.findById(book.getId()).block();
        assertEquals(newBook, actualBook);
    }

}
