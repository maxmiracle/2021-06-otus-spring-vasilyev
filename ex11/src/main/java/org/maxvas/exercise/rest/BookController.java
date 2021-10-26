package org.maxvas.exercise.rest;

import lombok.AllArgsConstructor;
import org.maxvas.exercise.domain.Book;
import org.maxvas.exercise.repositories.BookRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Flux<Book> allBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<Book> bookById(@PathVariable UUID id) {
        return bookRepository.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteBook(@PathVariable UUID id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<Book> postBook(@PathVariable UUID id, @RequestBody Book book) {
        // set given id
        book.setId(id);
        return bookRepository.save(book);
    }

    @PostMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Mono<Book> newBook(@RequestBody Book book){
        if (Optional.ofNullable(book.getId()).isEmpty())
        {
            book.setId(UUID.randomUUID());
        }
        return bookRepository.save(book);
    }
}
