package org.maxvas.repositories;

import org.maxvas.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title = :title")
    Book findByTitle(@Param("title") String title);

    @Override
    Book getById(Long id);

    //eager load author, genre entities
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @Override
    void delete(Book book);

    @Override
    Book save(Book book);
}
