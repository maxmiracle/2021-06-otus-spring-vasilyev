package org.maxvas.repositories;

import org.maxvas.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    @PostMapping("hasPermission(returnObject, 'READ')")
    @Query("select b from Book b where b.title = :title")
    Book findByTitle(@Param("title") String title);

    @PostMapping("hasPermission(returnObject, 'READ')")
    @Override
    Optional<Book> findById(Long id);

    //eager load author, genre entities
    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph(attributePaths = { "author", "genre" })
    List<Book> findAll();

    @PreAuthorize("hasPermission(#book, 'DELETE')")
    @Override
    void delete(Book book);

    @PreAuthorize("hasRole('ROLE_USER')||hasRole('ROLE_ADMIN')")
    @Override
    Book save(Book book);
}
