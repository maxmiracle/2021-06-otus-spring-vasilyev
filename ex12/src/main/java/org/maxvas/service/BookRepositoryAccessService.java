package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.domain.Book;
import org.maxvas.repositories.BookRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookRepositoryAccessService {

    private final BookRepository bookRepository;

    @PreAuthorize("hasRole('ROLE_USER')||hasRole('ROLE_ADMIN')")
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @PreAuthorize("hasRole('ROLE_USER')||hasRole('ROLE_ADMIN')")
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
