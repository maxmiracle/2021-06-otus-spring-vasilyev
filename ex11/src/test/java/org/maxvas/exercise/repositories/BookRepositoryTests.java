package org.maxvas.exercise.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Author;
import org.maxvas.exercise.domain.Book;
import org.maxvas.exercise.domain.Genre;
import org.maxvas.exercise.mongock.changelog.DatabaseChangelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableMongock
@DataMongoTest
class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void create() {
        String testName = "TestName";
        Author testAuthor = new Author(UUID.randomUUID(), "TestAuthor");
        Genre testGenre = new Genre(UUID.randomUUID(), "TestGenre");
        Book book = new Book(UUID.randomUUID(), testName, testAuthor, testGenre);
        Mono<Book> savedBook = bookRepository.save(book);
        StepVerifier.create(savedBook)
                .assertNext(book1 -> assertEquals(book, book1))
                .expectComplete()
                .verify();
    }


    @Test
    void findByTitle() {
        StepVerifier.create(bookRepository.findByTitle(DatabaseChangelog.dynamicComposition.getTitle()))
                .assertNext(book -> assertEquals(DatabaseChangelog.dynamicComposition, book))
                .expectComplete()
                .verify();
    }


}
