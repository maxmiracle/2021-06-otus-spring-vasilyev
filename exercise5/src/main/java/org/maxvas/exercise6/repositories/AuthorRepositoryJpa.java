package org.maxvas.exercise6.repositories;

import lombok.AllArgsConstructor;
import org.maxvas.exercise6.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        return em.createQuery(
                "select count(a) from Author a"
                , Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return author;
    }

    @Override
    public Optional<Author> findOne(UUID id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> findOneByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.name = :name"
                , Author.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery(
                "select a from Author a"
                , Author.class).getResultList();
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}
