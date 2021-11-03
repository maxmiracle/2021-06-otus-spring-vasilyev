package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.maxvas.domain.Author;
import org.maxvas.domain.Book;
import org.maxvas.domain.Genre;
import org.maxvas.repositories.AuthorRepository;
import org.maxvas.repositories.GenreRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepositoryAccessService bookRepositoryAccessService;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final MutableAclService mutableAclService;


    private void addRights(Book book) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        final Sid userRole = new GrantedAuthoritySid("ROLE_USER");
        final Sid adminRole = new GrantedAuthoritySid("ROLE_ADMIN");
        // Для тех, кто имеет роль USER можно редактировать и просматривать.
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, userRole, true);
        mutableAclService.updateAcl(acl);
        // Роль ADMIN - могут просматривать добавленные записи и удалять.
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, adminRole, true);
        mutableAclService.updateAcl(acl);
    }

    @Transactional
    @Override
    public Book createBook(String title, String authorName, String genreName) {
        Genre genre = findGenreByName(genreName).orElseGet(() -> newGenre(genreName));
        Author author = findAuthorByName(authorName).orElseGet(() -> newAuthor(authorName));
        Book book = bookRepositoryAccessService.save(new Book(null, title, author, genre));
        addRights(book);
        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findGenreByName(String name) {
        var genre = genreRepository.findByName(name);
        return Optional.ofNullable(genre);
    }

    @Transactional
    @Override
    public Genre newGenre(String name) {
        Genre newGenre = new Genre(null, name);
        Genre genre = genreRepository.save(newGenre);
        return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findAuthorByName(String name) {
        var author = authorRepository.findByName(name);
        return Optional.ofNullable(author);
    }

    @Transactional
    @Override
    public Author newAuthor(String name) {
        Author newAuthor = new Author(null, name);
        Author author = authorRepository.save(newAuthor);
        return author;
    }

    @Transactional
    @Override
    public void update(Long id, String title, String authorName, String genreName) {
        Book updatedBook = bookRepositoryAccessService.getById(id);
        Genre genre = findGenreByName(genreName).orElseGet(() -> newGenre(genreName));
        Author author = findAuthorByName(authorName).orElseGet(() -> newAuthor(authorName));
        updatedBook.setTitle(title);
        updatedBook.setAuthor(author);
        updatedBook.setGenre(genre);
        bookRepositoryAccessService.save(updatedBook);
    }

    @Transactional(readOnly = true)
    @Override
    public String allBooksInfo() {
        return bookRepositoryAccessService.findAll().stream()
                .map(book -> ToStringBuilder.reflectionToString(book, ToStringStyle.NO_CLASS_NAME_STYLE))
                .collect(Collectors.joining(",\n"));
    }

    @Transactional(readOnly = true)
    @Override
    public String bookInfo(Long id) {
        Book book = bookRepositoryAccessService.getById(id);
        return ToStringBuilder.reflectionToString(book, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Transactional
    @Override
    public Book getById(Long id) {
        return bookRepositoryAccessService.getById(id);
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        Book book = bookRepositoryAccessService.getById(id);
        bookRepositoryAccessService.delete(book);
    }


    @Transactional
    @Override
    public List<Book> findAll() {
        return bookRepositoryAccessService.findAll();
    }
}
