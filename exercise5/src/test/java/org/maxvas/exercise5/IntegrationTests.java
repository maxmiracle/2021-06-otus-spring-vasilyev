package org.maxvas.exercise5;

import org.junit.jupiter.api.Test;
import org.maxvas.exercise5.dao.BookDao;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class IntegrationTests {

    private static final String COMMAND_CREATE_BOOK = "create --title BookTitle1 --author Author1 --genre Genre1";
    private static final String COMMAND_DELETE_BOOK = "delete --id %s";
    private static final String SHOW_ALL_BOOKS = "all";
    @Autowired
    BookService bookService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private Shell shell;

    @Test
    public void createBook() {
        UUID bookId = bookService.createBook("TitleN0", "Author A0. A0.", "Genre0");
        Optional<Book> book = bookDao.getById(bookId);
        assertTrue(book.isPresent());
        String title = book.get().getTitle();
        assertEquals("TitleN0", title);
    }

    @Test
    public void createBookShell() {
        String newBookId = (String) shell.evaluate(() -> COMMAND_CREATE_BOOK);
        UUID bookId = UUID.fromString(newBookId);
        Optional<Book> book = bookDao.getById(bookId);
        assertTrue(book.isPresent());
        assertEquals("BookTitle1", book.get().getTitle());
    }

    @Test
    public void deleteBookShell() {
        String allBooks = (String) shell.evaluate(() -> SHOW_ALL_BOOKS);
        UUID idBookToDelete = getFirstBookId(allBooks);
        Optional<Book> book = bookDao.getById(idBookToDelete);
        assertTrue(book.isPresent());
        shell.evaluate(() -> String.format(COMMAND_DELETE_BOOK, idBookToDelete));
        Optional<Book> deletedBook = bookDao.getById(idBookToDelete);
        assertTrue(deletedBook.isEmpty());
    }

    private UUID getFirstBookId(String allBookList) {
        String findBookIdRegex = "(?<=,id\\=)[0-f\\-]*";
        Pattern findBookIdRegexPattern = Pattern.compile(findBookIdRegex);
        Matcher matcher = findBookIdRegexPattern.matcher(allBookList);
        if (matcher.find()) {
            String uuid = matcher.group();
            return UUID.fromString(uuid);
        } else {
            return null;
        }
    }

}
