package org.maxvas.exercise6.repositories;

import lombok.AllArgsConstructor;
import org.maxvas.exercise6.domain.Author;
import org.maxvas.exercise6.domain.Comment;
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
public class CommentRepositoryJpa implements CommentRepository {


    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        return em.createQuery(
                "select count(c) from Comment c"
                , Long.class).getSingleResult();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public Optional<Comment> findOne(UUID id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(UUID bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.book_id = :book_id"
                , Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery(
                "select c from Comment c"
                , Comment.class).getResultList();
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
