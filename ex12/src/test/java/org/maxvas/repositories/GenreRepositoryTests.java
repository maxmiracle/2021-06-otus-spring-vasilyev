package org.maxvas.repositories;

import org.junit.jupiter.api.Test;
import org.maxvas.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Long genreIdToDelete = genreList.get(0).getId();
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
        Long genreId = genreList.get(0).getId();
        Genre updatedGenre = new Genre(genreId, newName);
        genreRepository.save(updatedGenre);
        Optional<Genre> newUpdatedGenre = genreRepository.findById(genreId);
        assertEquals(newName, newUpdatedGenre.get().getName());
    }

}
