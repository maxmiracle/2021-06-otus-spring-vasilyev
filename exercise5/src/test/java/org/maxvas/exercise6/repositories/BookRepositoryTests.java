package org.maxvas.exercise6.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.domain.Genre;
import org.maxvas.exercise6.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryTests {

    private static int EXPECTED_COUNT_FETCH_ALL = 1;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void create() {
        Long count = bookRepository.count();
        String testTitle = "Title";
        Author newAuthor = new Author(null, "New author");
        Genre newGenre = new Genre(null, "New genre");
        entityManager.persist(newAuthor);
        entityManager.persist(newGenre);

        Book book = new Book(null, testTitle, newAuthor, newGenre);
        Book book1 = bookRepository.save(book);
        Optional<Book> savedBook = bookRepository.findOne(book1.getId());

        assertTrue(savedBook.isPresent());
        assertEquals(testTitle, savedBook.get().getTitle());
        assertEquals(count + 1, bookRepository.count());
    }

    @Test
    public void delete() {
        List<Book> bookList = bookRepository.findAll();
        UUID bookIdToDelete = bookList.get(0).getId();
        bookRepository.deleteById(bookIdToDelete);
        Optional<Book> deletedBook = bookRepository.findOne(bookIdToDelete);
        assertTrue(deletedBook.isEmpty());
    }

    @Test
    public void getByName() {
        List<Book> bookList = bookRepository.findAll();
        String title = bookList.get(0).getTitle();
        Optional<Book> otherBook = bookRepository.findOneByTitle(title);
        assertTrue(otherBook.isPresent());
        assertEquals(bookList.get(0), otherBook.get());
    }

    @Test
    public void update() {
        String updTitle = "Upd title";
        List<Book> bookList = bookRepository.findAll();
        UUID bookId = bookList.get(0).getId();
        Book updatedBook = bookRepository.findOne(bookId).get();
        updatedBook.setTitle(updTitle);
        bookRepository.save(updatedBook);
        Optional<Book> newUpdatedBook = bookRepository.findOne(bookId);
        assertEquals(updTitle, newUpdatedBook.get().getTitle());
    }

    @Test
    public void findAll() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        List<Book> bookList = bookRepository.findAll();
        assertEquals(EXPECTED_COUNT_FETCH_ALL, sessionFactory.getStatistics().getPrepareStatementCount());
    }

}
