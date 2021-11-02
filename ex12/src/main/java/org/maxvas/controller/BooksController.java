package org.maxvas.controller;

import lombok.AllArgsConstructor;
import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;
import org.maxvas.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


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

    @PostMapping("/")
    public String listBooksForward(Model model) {
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String get(
            @RequestParam(value = "id") Long id,
            Model model
    ) {
        Book book = bookService.getById(id);
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
            @RequestParam(value = "id") Long id) {
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
