package org.maxvas.exercise5;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise5.dao.GenreDao;
import org.maxvas.exercise5.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GenreDaoTests {

    @Autowired
    private GenreDao genreDao;


    @Test
    void create() {
        int count = genreDao.count();
        String testName = "Name";
        Genre genre = new Genre()
                .setName("Name");
        UUID genreID = genreDao.insert(genre);
        Optional<Genre> savedGenre = genreDao.getById(genreID);
        assertTrue(savedGenre.isPresent());
        assertEquals(testName, savedGenre.get().getName());
        assertEquals(count + 1, genreDao.count());
    }

    @Test
    void delete() {
        List<Genre> genreList = genreDao.getAll();
        UUID genreIdToDelete = genreList.get(0).getId();
        genreDao.deleteById(genreIdToDelete);
        Optional<Genre> deletedGenre = genreDao.getById(genreIdToDelete);
        assertTrue(deletedGenre.isEmpty());
    }

    @Test
    void getByName() {
        List<Genre> genreList = genreDao.getAll();
        String name = genreList.get(0).getName();
        Optional<Genre> otherGenre = genreDao.getByName(name);
        assertTrue(otherGenre.isPresent());
        assertEquals(genreList.get(0), otherGenre.get());
    }

    @Test
    void update() {
        String newName = "New Name Genre";
        List<Genre> genreList = genreDao.getAll();
        UUID genreId = genreList.get(0).getId();
        Genre updatedGenre = new Genre().setId(genreId)
                .setName(newName);
        genreDao.update(updatedGenre);
        Optional<Genre> newUpdatedGenre = genreDao.getById(genreId);
        assertEquals(newName, newUpdatedGenre.get().getName());
    }

}
