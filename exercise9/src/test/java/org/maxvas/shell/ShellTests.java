package org.maxvas.shell;

import org.junit.jupiter.api.Test;
import org.maxvas.domain.Book;
import org.maxvas.repositories.BookRepository;
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
class ShellTests {

    private static final String COMMAND_CREATE_BOOK = "create --title BookTitle1 --author Author1 --genre Genre1";
    private static final String COMMAND_DELETE_BOOK = "delete --id %s";
    private static final String SHOW_ALL_BOOKS = "all";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Shell shell;

    @Test
    public void createBookShell() {
        // act
        String newBookId = (String) shell.evaluate(() -> COMMAND_CREATE_BOOK);
        // assert
        UUID bookId = UUID.fromString(newBookId);
        Optional<Book> book = bookRepository.findById(bookId);
        assertTrue(book.isPresent());
        assertEquals("BookTitle1", book.get().getTitle());
    }

    @Test
    public void deleteBookShell() {
        // prepare
        String allBooks = (String) shell.evaluate(() -> SHOW_ALL_BOOKS);
        UUID idBookToDelete = getFirstBookId(allBooks);
        Optional<Book> book = bookRepository.findById(idBookToDelete);
        assertTrue(book.isPresent());
        // act
        shell.evaluate(() -> String.format(COMMAND_DELETE_BOOK, idBookToDelete));
        Optional<Book> deletedBook = bookRepository.findById(idBookToDelete);
        // assert
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
