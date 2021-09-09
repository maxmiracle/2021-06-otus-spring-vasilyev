package org.maxvas.exercise5;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise5.dao.AuthorDaoJdbc;
import org.maxvas.exercise5.dao.BookDao;
import org.maxvas.exercise5.dao.BookDaoJdbc;
import org.maxvas.exercise5.dao.GenreDaoJdbc;
import org.maxvas.exercise5.domain.Author;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.domain.Genre;
import org.maxvas.exercise5.service.BookService;
import org.maxvas.exercise5.service.BookServiceImpl;
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

    @Autowired
    private BookService bookService;


    @Test
    public void create() {
        int count = bookDao.count();
        String testTitle = "Title";
        Author newAuthor = bookService.getAuthorByName("New author");
        Genre newGenre = bookService.getGenreByName("New genre");
        Book book = new Book()
                .setTitle(testTitle)
                .setAuthor(newAuthor)
                .setGenre(newGenre);
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
        String newTitle = "New Name Book";
        Author updAuthor = bookService.getAuthorByName("Upd author");
        Genre updGenre = bookService.getGenreByName("Upd genre");
        List<Book> bookList = bookDao.getAll();
        UUID bookId = bookList.get(0).getId();
        Book updatedBook = new Book().setId(bookId)
                .setTitle(newTitle)
                .setAuthor(updAuthor)
                .setGenre(updGenre);
        bookDao.update(updatedBook);
        Optional<Book> newUpdatedBook = bookDao.getById(bookId);
        assertEquals(newTitle, newUpdatedBook.get().getTitle());
        assertEquals(updAuthor, newUpdatedBook.get().getAuthor());
        assertEquals(updGenre, newUpdatedBook.get().getGenre());
    }

}
