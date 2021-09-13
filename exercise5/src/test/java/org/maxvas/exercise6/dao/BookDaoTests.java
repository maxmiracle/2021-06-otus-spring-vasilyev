package org.maxvas.exercise6.dao;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.domain.Genre;
import org.maxvas.exercise6.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({BookDaoJdbc.class, BookServiceImpl.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class BookDaoTests {

    @Autowired
    private BookDao bookDao;


    @Test
    public void create() {
        int count = bookDao.count();
        String testTitle = "Title";
        Author newAuthor = new Author(null, "New author");
        Genre newGenre = new Genre(null, "New genre");
        Book book = new Book(null, testTitle, newAuthor, newGenre);
        UUID bookID = bookDao.insert(book);
        Optional<Book> savedBook = bookDao.getById(bookID);
        assertTrue(savedBook.isPresent());
        assertEquals(testTitle, savedBook.get().getTitle());
        assertEquals(count + 1, bookDao.count());
    }

    @Test
    public void delete() {
        List<Book> bookList = bookDao.getAll();
        UUID bookIdToDelete = bookList.get(0).getId();
        bookDao.deleteById(bookIdToDelete);
        Optional<Book> deletedBook = bookDao.getById(bookIdToDelete);
        assertTrue(deletedBook.isEmpty());
    }

    @Test
    public void getByName() {
        List<Book> bookList = bookDao.getAll();
        String title = bookList.get(0).getTitle();
        Optional<Book> otherBook = bookDao.getByTitle(title);
        assertTrue(otherBook.isPresent());
        assertEquals(bookList.get(0), otherBook.get());
    }

    @Test
    public void update() {
        String updTitle = "Upd title";
        List<Book> bookList = bookDao.getAll();
        UUID bookId = bookList.get(0).getId();
        Book updatedBook = bookDao.getById(bookId).get();
        updatedBook.setTitle(updTitle);
        bookDao.update(updatedBook);
        Optional<Book> newUpdatedBook = bookDao.getById(bookId);
        assertEquals(updTitle, newUpdatedBook.get().getTitle());

    }

}
