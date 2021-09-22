package org.maxvas.exercise6.repositories;

import lombok.AllArgsConstructor;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        return em.createQuery(
                "select count(a) from Book a"
                , Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public Optional<Book> findOne(UUID id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.title = :title"
                , Book.class);
        query.setParameter("title", title);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> bookGraph = em.getEntityGraph("book-graph");
        return em.createQuery("From Book", Book.class)
                .setHint(
                        "javax.persistence.fetchgraph", bookGraph)
                .getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void deleteById(UUID id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
