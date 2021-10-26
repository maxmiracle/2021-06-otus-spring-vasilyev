package org.maxvas.exercise.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Author;
import org.maxvas.exercise.domain.Author;
import org.maxvas.exercise.mongock.changelog.DatabaseChangelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableMongock
@DataMongoTest
class AuthorRepositoryTests {


    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void create() {
        String testName = "TestName";
        Author author = new Author(UUID.randomUUID(), testName);
        Mono<Author> savedAuthor = authorRepository.save(author);
        StepVerifier.create(savedAuthor)
                .assertNext(author1 -> assertEquals(author, author1))
                .expectComplete()
                .verify();
    }

    @Test
    void findByName() {
        Mono<Author> author = authorRepository.findByName(DatabaseChangelog.authorWebb.getName());
        StepVerifier
                .create(author)
                .assertNext(author1 -> assertEquals(DatabaseChangelog.authorWebb, author1))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Author author = authorRepository.findById(DatabaseChangelog.authorKandinsky.getId()).block();
        Author newAuthor = author.setName("Ян Полак");
        Mono<Author>  savedAuthor = authorRepository.save(newAuthor);
        StepVerifier
                .create(savedAuthor)
                .assertNext(author1 -> assertEquals(newAuthor, author1))
                .expectComplete()
                .verify();
    }

}
