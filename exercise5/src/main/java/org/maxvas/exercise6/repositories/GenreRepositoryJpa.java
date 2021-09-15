package org.maxvas.exercise6.repositories;

import lombok.AllArgsConstructor;
import org.maxvas.exercise6.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GenreRepositoryJpa implements GenreRepository{
    
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        return em.createQuery(
                "select count(a) from Genre a"
                , Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        return genre;
    }

    @Override
    public Optional<Genre> findOne(UUID id) {
        TypedQuery<Genre> query = em.createQuery(
                "select a from Genre a where a.id = :id"
                , Genre.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> findOneByName(String name) {
        TypedQuery<Genre> query = em.createQuery(
                "select a from Genre a where a.name = :name"
                , Genre.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery(
                "select a from Genre a"
                , Genre.class).getResultList();
    }

    @Override
    public void delete(Genre genre) {
        Genre mergedGenre = em.merge(genre);
        em.remove(mergedGenre);
    }
}
