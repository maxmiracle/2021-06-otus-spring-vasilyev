package org.maxvas.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryTests {

    private static final int EXPECTED_COUNT_FETCH_ALL = 1;

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
        Optional<Book> savedBook = bookRepository.findById(book1.getId());

        assertTrue(savedBook.isPresent());
        assertEquals(testTitle, savedBook.get().getTitle());
        assertEquals(count + 1, bookRepository.count());
    }

    @Test
    public void delete() {
        List<Book> bookList = bookRepository.findAll();
        Long bookIdToDelete = bookList.get(0).getId();
        bookRepository.deleteById(bookIdToDelete);
        Optional<Book> deletedBook = bookRepository.findById(bookIdToDelete);
        assertTrue(deletedBook.isEmpty());
    }

    @Test
    public void getByName() {
        List<Book> bookList = bookRepository.findAll();
        String title = bookList.get(0).getTitle();
        Book otherBook = bookRepository.findByTitle(title);
        assertEquals(bookList.get(0), otherBook);
    }

    @Test
    public void update() {
        String updTitle = "Upd title";
        List<Book> bookList = bookRepository.findAll();
        Long bookId = bookList.get(0).getId();
        Book updatedBook = bookRepository.findById(bookId).get();
        updatedBook.setTitle(updTitle);
        bookRepository.save(updatedBook);
        Optional<Book> newUpdatedBook = bookRepository.findById(bookId);
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
