package org.maxvas.exercise6.dao;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoTests {

    @Autowired
    private AuthorDao authorDao;


    @Test
    void create() {
        int count = authorDao.count();
        String testName = "Name";
        Author author = new Author(null, testName);
        UUID authorID = authorDao.insert(author);
        Optional<Author> savedAuthor = authorDao.getById(authorID);
        assertTrue(savedAuthor.isPresent());
        assertEquals(testName, savedAuthor.get().getName());
        assertEquals(count + 1, authorDao.count());
    }

    @Test
    void delete() {
        List<Author> authorList = authorDao.getAll();
        UUID authorIdToDelete = authorList.get(0).getId();
        authorDao.deleteById(authorIdToDelete);
        Optional<Author> deletedAuthor = authorDao.getById(authorIdToDelete);
        assertTrue(deletedAuthor.isEmpty());
    }

    @Test
    void getByName() {
        List<Author> authorList = authorDao.getAll();
        String name = authorList.get(0).getName();
        Optional<Author> otherAuthor = authorDao.getByName(name);
        assertTrue(otherAuthor.isPresent());
        assertEquals(authorList.get(0), otherAuthor.get());
    }

    @Test
    void update() {
        String newName = "New Name Author";
        List<Author> authorList = authorDao.getAll();
        UUID authorId = authorList.get(0).getId();
        Author updatedAuthor = new Author(authorId, newName);
        authorDao.update(updatedAuthor);
        Optional<Author> newUpdatedAuthor = authorDao.getById(authorId);
        assertEquals(newName, newUpdatedAuthor.get().getName());
    }

}
