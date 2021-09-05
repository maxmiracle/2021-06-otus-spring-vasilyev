package org.maxvas.exercise5.dao;

import lombok.AllArgsConstructor;
import org.maxvas.exercise5.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(id) from author", Map.of(), Integer.class);
    }

    @Override
    public UUID insert(Author author) {
        return namedParameterJdbcOperations.queryForObject("SELECT ID FROM FINAL TABLE (insert into author (name) values (:name))",
                Map.of("name", author.getName()),
                UUID.class);
    }

    @Override
    public void update(Author author) {
        namedParameterJdbcOperations.update("update author set name=:name where id=:id ",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public Optional<Author> getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            Author author = namedParameterJdbcOperations.queryForObject(
                    "select id, name from author where id = :id", params, new AuthorMapper()
            );
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            Author author = namedParameterJdbcOperations.queryForObject(
                    "select id, name from author where name = :name", params, new AuthorMapper()
            );
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select id, name from author", new AuthorMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from author where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = resultSet.getObject("id", UUID.class);
            String name = resultSet.getString("name");
            return new Author()
                    .setId(id)
                    .setName(name);
        }
    }
}
