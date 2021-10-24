package org.maxvas.exercise.repositories;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
class AuthorRepositoryTests {


    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void create() {
        long count = authorRepository.count();
        String testName = "Name";
        Author author = new Author(UUID.randomUUID(), testName);
        Author savedAuthor = authorRepository.save(author);
        assertEquals(count + 1, authorRepository.count());
    }

    @Test
    void delete() {
        List<Author> authorList = authorRepository.findAll();
        UUID authorIdToDelete = authorList.get(0).getId();
        authorRepository.delete(authorList.get(0));
        Optional<Author> deletedAuthor = authorRepository.findById(authorIdToDelete.toString());
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
        assertEquals(updatedAuthor, newUpdatedAuthor.get());
    }

}
