package org.maxvas.rest;

import lombok.AllArgsConstructor;
import org.maxvas.domain.Book;
import org.maxvas.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {
    BookService bookService;

    @GetMapping(value = "/", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Book> allBooks() {
        List<Book> books = bookService.findAll();
        return books;
    }


}
