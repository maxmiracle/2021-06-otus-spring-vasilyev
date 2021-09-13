package org.maxvas.exercise6.service;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.dao.AuthorDaoJdbc;
import org.maxvas.exercise6.dao.BookDao;
import org.maxvas.exercise6.dao.BookDaoJdbc;
import org.maxvas.exercise6.dao.GenreDaoJdbc;
import org.maxvas.exercise6.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({BookDaoJdbc.class, BookServiceImpl.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    @Test
    public void createBook() {
        UUID bookId = bookService.createBook("TitleN0", "Author A0. A0.", "Genre0");
        Optional<Book> book = bookDao.getById(bookId);
        assertTrue(book.isPresent());
        String title = book.get().getTitle();
        assertEquals("TitleN0", title);
    }
}
