package org.maxvas.exercise5.service;

import lombok.AllArgsConstructor;
import org.maxvas.exercise5.dao.AuthorDao;
import org.maxvas.exercise5.dao.BookDao;
import org.maxvas.exercise5.dao.GenreDao;
import org.maxvas.exercise5.domain.Author;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final GenreDao genreDao;

    private final AuthorDao authorDao;

    public UUID createBook(String title, String authorName, String genreName) {
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
        return bookDao.insert(new Book()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre));
    }

    public Genre getGenreByName(String name) {
        var genre  = genreDao.getByName(name);
        if (genre.isEmpty()) {
            Genre newGenre = new Genre().setName(name);
            UUID id = genreDao.insert((new Genre()).setName(name));
            newGenre.setId(id);
            return newGenre;
        } else {
            return genre.get();
        }
    }

    public Author getAuthorByName(String name) {
        var author  = authorDao.getByName(name);
        if (author.isEmpty()) {
            Author newAuthor = new Author().setName(name);
            UUID id = authorDao.insert((new Author()).setName(name));
            newAuthor.setId(id);
            return newAuthor;
        } else {
            return author.get(); 
        }
    }

    public void update(UUID id, String title, String authorName, String genreName) {
        Genre genre = getGenreByName(genreName);
        Author author = getAuthorByName(authorName);
        Book updatedBook = new Book()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre);
        bookDao.update(updatedBook);
    }
}
