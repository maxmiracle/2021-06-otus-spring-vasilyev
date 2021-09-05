package org.maxvas.exercise5.dao;

import lombok.AllArgsConstructor;
import org.maxvas.exercise5.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

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
                Map.of("title", book.getTitle(), "author_id", book.getAuthorId(), "genre_id", book.getGenreId()),
                UUID.class);
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update("update book set title=:title, author_id=:author_id, genre_id=:genre_id where id=:id ",
                Map.of("id", book.getId(), "title", book.getTitle(), "author_id", book.getAuthorId(), "genre_id", book.getGenreId()));
    }

    @Override
    public Optional<Book> getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select id, title, author_id, genre_id from book where id = :id", params, new BookMapper()));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select id, title, author_id, genre_id from book", new BookMapper());
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
            UUID genreId = resultSet.getObject("genre_id", java.util.UUID.class);
            return new Book()
                    .setId(id)
                    .setTitle(title)
                    .setAuthorId(authorId)
                    .setGenreId(genreId);
        }
    }
}
