package org.maxvas.exercise6.repositories;

import lombok.AllArgsConstructor;
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
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id = :id"
                , Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
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
        return em.createQuery(
                "select b from Book b"
                , Book.class).getResultList();
    }

    @Override
    public void delete(Book book) {
        Book mergedBook = em.merge(book);
        em.remove(mergedBook);
    }

    @Override
    public void deleteById(UUID id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
