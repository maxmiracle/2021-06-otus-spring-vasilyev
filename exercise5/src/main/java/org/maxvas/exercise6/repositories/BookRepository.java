package org.maxvas.exercise6.repositories;

import org.maxvas.exercise6.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    //special for exercise
    @Query("select b from Book b where b.title = :title")
    Book findByTitle(@Param("title") String title);

    //eager load author, genre entities
    @EntityGraph(attributePaths = { "author", "genre" })
    List<Book> findAll();
}
