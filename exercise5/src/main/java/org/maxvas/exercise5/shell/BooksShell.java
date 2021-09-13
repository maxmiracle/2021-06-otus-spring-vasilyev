package org.maxvas.exercise5.shell;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.maxvas.exercise5.dao.BookDao;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.service.BookService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class BooksShell {

    private final static String defaultEmpty = null;
    private final BookDao bookDao;
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
        UUID bookId = bookService.createBook(title, author, genre);
        return bookId.toString();
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
