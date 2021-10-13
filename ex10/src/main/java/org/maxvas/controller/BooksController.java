package org.maxvas.controller;

import lombok.AllArgsConstructor;
import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;
import org.maxvas.exception.NotFoundException;
import org.maxvas.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class BooksController {
    private final BookService bookService;


    @GetMapping("/")
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String get(
            @RequestParam(value = "id") UUID id,
            Model model
    ) {
        Book book = bookService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String put(
            @ModelAttribute Book book
    ) {
        bookService.update(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam(value = "id") UUID id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String newBook(
            Model model
    ) {
        Book newBook = new Book()
                .setAuthor(new Author())
                .setGenre((new Genre()));
        model.addAttribute("book", newBook);
        return "create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Book book
    ) {
        bookService.createBook(book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
        return "redirect:/";
    }

}
