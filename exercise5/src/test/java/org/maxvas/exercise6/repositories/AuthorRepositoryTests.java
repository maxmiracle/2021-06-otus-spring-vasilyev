package org.maxvas.exercise6.repositories;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Author;
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
class AuthorRepositoryTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void create() {
        long count = authorRepository.count();
        String testName = "Name";
        Author author = new Author(null, testName);
        Author savedAuthor = authorRepository.save(author);
        Author actualSavedAuthor = em.find(Author.class, savedAuthor.getId());
        assertEquals(author, actualSavedAuthor);
        assertEquals(count + 1, authorRepository.count());
    }

    @Test
    void delete() {
        List<Author> authorList = authorRepository.findAll();
        UUID authorIdToDelete = authorList.get(0).getId();
        authorRepository.delete(authorList.get(0));
        Optional<Author> deletedAuthor = authorRepository.findById(authorIdToDelete);
        assertTrue(deletedAuthor.isEmpty());
    }

    @Test
    void getByName() {
        List<Author> authorList = authorRepository.findAll();
        String name = authorList.get(0).getName();
        Author otherAuthor = authorRepository.findByName(name);
        assertEquals(authorList.get(0), otherAuthor);
    }

    @Test
    void update() {
        String newName = "New Name Author";
        List<Author> authorList = authorRepository.findAll();
        UUID authorId = authorList.get(0).getId();
        Author updatedAuthor = new Author(authorId, newName);
        authorRepository.save(updatedAuthor);
        Optional<Author> newUpdatedAuthor = authorRepository.findById(authorId);
        assertEquals(newName, newUpdatedAuthor.get().getName());
    }

}
