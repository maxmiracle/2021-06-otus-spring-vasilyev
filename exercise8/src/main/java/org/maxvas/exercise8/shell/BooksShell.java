package org.maxvas.exercise8.shell;

import lombok.AllArgsConstructor;
import org.maxvas.exercise8.domain.Book;
import org.maxvas.exercise8.service.BookService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.UUID;

@ShellComponent
@AllArgsConstructor
public class BooksShell {

    private final static String defaultEmpty = null;
    private final BookService bookService;

    @ShellMethod(value = "Show all books", key = {"all", "a"})
    public String showAllBooks() {
        return bookService.allBooksInfo();
    }

    @ShellMethod(value = "Create book", key = {"create", "c"})
    public String create(
            @ShellOption String title,
            @ShellOption String author,
            @ShellOption String genre) {
        Book book = bookService.createBook(title, author, genre);
        return book.getId().toString();
    }

    @ShellMethod(value = "Read book", key = {"read", "r"})
    public String read(
            @ShellOption UUID id) {
        return bookService.bookInfo(id);
    }

    @ShellMethod(value = "Delete book", key = {"delete", "d"})
    public void delete(
            @ShellOption UUID id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "Update book", key = {"update", "u"})
    public void update(@ShellOption UUID id,
                       @ShellOption String title,
                       @ShellOption String author,
                       @ShellOption String genre) {
        bookService.update(id, title, author, genre);
    }


}
