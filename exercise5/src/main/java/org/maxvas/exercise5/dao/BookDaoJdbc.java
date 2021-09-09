package org.maxvas.exercise5.dao;

import lombok.AllArgsConstructor;
import org.maxvas.exercise5.domain.Author;
import org.maxvas.exercise5.domain.Book;
import org.maxvas.exercise5.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(id) from book", Map.of(), Integer.class);
    }

    @Override
    public UUID insert(Book book) {
        return namedParameterJdbcOperations.queryForObject("SELECT ID FROM FINAL TABLE (insert into book (title, author_id, genre_id) values (:title, :author_id, :genre_id))",
                Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId()),
                UUID.class);
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update("update book set title=:title, author_id=:author_id, genre_id=:genre_id where id=:id ",
                Map.of("id", book.getId(), "title", book.getTitle(), "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId()));
    }

    @Override
    public Optional<Book> getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select b.id as id, b.title as title, b.author_id as author_id, a.name as author_name, b.genre_id as genre_id, g.name as genre_name from book as b, genre as g, author as a\n" +
                            " where a.id = b.author_id and g.id = b.genre_id and  b.id=:id", params, new BookMapper()));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getByTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select b.id as id, b.title as title, b.author_id as author_id, a.name as author_name, b.genre_id as genre_id, g.name as genre_name from book as b, genre as g, author as a\n" +
                            " where a.id = b.author_id and g.id = b.genre_id and  b.title=:title", params, new BookMapper()));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id as id, b.title as title, b.author_id as author_id, a.name as author_name, b.genre_id as genre_id, g.name as genre_name from book as b, genre as g, author as a\n" +
                " where a.id = b.author_id and g.id = b.genre_id", new BookMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = resultSet.getObject("id", UUID.class);
            String title = resultSet.getString("title");
            UUID authorId = resultSet.getObject("author_id", java.util.UUID.class);
            String authorName = resultSet.getString("author_name");
            UUID genreId = resultSet.getObject("genre_id", java.util.UUID.class);
            String genreName = resultSet.getString("genre_name");

            Author author = new Author()
                    .setName(authorName)
                    .setId(authorId);
            Genre genre = new Genre()
                    .setName(genreName)
                    .setId(genreId);
            return new Book()
                    .setId(id)
                    .setTitle(title)
                    .setAuthor(author)
                    .setGenre(genre);
        }
    }
}
