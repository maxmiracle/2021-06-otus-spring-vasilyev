package org.maxvas.exercise.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Genre;
import org.maxvas.exercise.mongock.changelog.DatabaseChangelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableMongock
@DataMongoTest
class GenreRepositoryTests {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    @Test
    void create() {
        String testName = "TestName";
        Genre genre = new Genre(UUID.randomUUID(), testName);
        Mono<Genre> savedGenre = genreRepository.save(genre);
        StepVerifier.create(savedGenre)
                .assertNext(genre1 -> assertEquals(genre, genre1))
                .expectComplete()
                .verify();
    }

    @Test
    void findByName() {
        Mono<Genre> genre = genreRepository.findByName(DatabaseChangelog.genreMonography.getName());
        StepVerifier
                .create(genre)
                .assertNext(genre1 -> assertEquals(DatabaseChangelog.genreMonography, genre1))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Genre genre = genreRepository.findById(DatabaseChangelog.genreSciFi.getId()).block();
        Genre newGenre = genre.setName("Novel");
        Mono<Genre>  savedGenre = genreRepository.save(newGenre);
        StepVerifier
                .create(savedGenre)
                .assertNext(genre1 -> assertEquals(newGenre, genre1))
                .expectComplete()
                .verify();
    }

}
