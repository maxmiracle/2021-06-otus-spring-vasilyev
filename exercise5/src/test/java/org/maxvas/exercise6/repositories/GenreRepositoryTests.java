package org.maxvas.exercise6.repositories;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Genre;
import org.maxvas.exercise6.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenreRepositoryTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreRepository;


    @Test
    void create() {
        long count = genreRepository.count();
        String testName = "Name";
        Genre genre = new Genre(null, testName);
        Genre savedGenre = genreRepository.save(genre);
        assertEquals(testName, savedGenre.getName());
        assertEquals(count + 1, genreRepository.count());
    }

    @Test
    void delete() {
        List<Genre> genreList = genreRepository.findAll();
        UUID genreIdToDelete = genreList.get(0).getId();
        genreRepository.delete(genreList.get(0));
        Optional<Genre> deletedGenre = genreRepository.findById(genreIdToDelete);
        assertTrue(deletedGenre.isEmpty());
    }

    @Test
    void getByName() {
        List<Genre> genreList = genreRepository.findAll();
        String name = genreList.get(0).getName();
        Genre otherGenre = genreRepository.findByName(name);
        assertEquals(genreList.get(0), otherGenre);
    }

    @Test
    void update() {
        String newName = "New Name Genre";
        List<Genre> genreList = genreRepository.findAll();
        UUID genreId = genreList.get(0).getId();
        Genre updatedGenre = new Genre(genreId, newName);
        genreRepository.save(updatedGenre);
        Optional<Genre> newUpdatedGenre = genreRepository.findById(genreId);
        assertEquals(newName, newUpdatedGenre.get().getName());
    }

}
