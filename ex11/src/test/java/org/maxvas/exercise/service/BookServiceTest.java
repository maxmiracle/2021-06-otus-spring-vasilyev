package org.maxvas.exercise.service;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import(BookServiceImpl.class)
class BookServiceTest {

    @Autowired
    private BookService bookService;


    @Test
    public void createBook() {
        Book bookInitial = bookService.createBook("TitleN0", "Author A0. A0.", "Genre0");
        //Book book = em.find(Book.class, bookInitial.getId());
        //assertEquals(bookInitial, book);
    }
}
