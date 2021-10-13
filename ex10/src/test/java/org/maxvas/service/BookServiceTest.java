package org.maxvas.service;

import org.junit.jupiter.api.Test;
import org.maxvas.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(BookServiceImpl.class)
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private TestEntityManager em;

    @Test
    public void createBook() {
        Book bookInitial = bookService.createBook("TitleN0", "Author A0. A0.", "Genre0");
        Book book = em.find(Book.class, bookInitial.getId());
        assertEquals(bookInitial, book);
    }
}
