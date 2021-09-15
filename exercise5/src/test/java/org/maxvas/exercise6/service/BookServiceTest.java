package org.maxvas.exercise6.service;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise6.domain.Book;
import org.maxvas.exercise6.repositories.AuthorRepositoryJpa;
import org.maxvas.exercise6.repositories.BookRepository;
import org.maxvas.exercise6.repositories.BookRepositoryJpa;
import org.maxvas.exercise6.repositories.GenreRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({BookRepositoryJpa.class, BookServiceImpl.class, GenreRepositoryJpa.class, AuthorRepositoryJpa.class})
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;



    @Test
    public void createBook() {
        Book bookInitial = bookService.createBook("TitleN0", "Author A0. A0.", "Genre0");
        Optional<Book> book = bookRepository.findOne(bookInitial.getId());
        assertTrue(book.isPresent());
        String title = book.get().getTitle();
        assertEquals("TitleN0", title);
    }
}
