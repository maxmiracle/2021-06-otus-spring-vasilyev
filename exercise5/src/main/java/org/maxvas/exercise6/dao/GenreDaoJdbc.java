package org.maxvas.exercise6.dao;

import lombok.AllArgsConstructor;
import org.maxvas.exercise6.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(id) from genre", Map.of(), Integer.class);
    }

    @Override
    public UUID insert(Genre genre) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", genre.getName());
        namedParameterJdbcOperations.update("insert into genre (name) values (:name)",
                sqlParameterSource,
                generatedKeyHolder);
        return generatedKeyHolder.getKeyAs(UUID.class);
    }

    @Override
    public void update(Genre genre) {
        namedParameterJdbcOperations.update("update genre set name=:name where id=:id ",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Optional<Genre> getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            Genre genre = namedParameterJdbcOperations.queryForObject(
                    "select id, name from genre where id = :id", params, new GenreMapper()
            );
            return Optional.ofNullable(genre);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            Genre genre = namedParameterJdbcOperations.queryForObject(
                    "select id, name from genre where name = :name", params, new GenreMapper()
            );
            return Optional.ofNullable(genre);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select id, name from genre", new GenreMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from genre where id = :id", params
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = resultSet.getObject("id", UUID.class);
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
