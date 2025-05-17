package com.anima.graphql.service;

import com.anima.graphql.domain.dto.BookInput;
import com.anima.graphql.domain.dto.BookUpdateInput;
import com.anima.graphql.domain.model.Author;
import com.anima.graphql.domain.model.Book;
import com.anima.graphql.repository.AuthorRepository;
import com.anima.graphql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Book addBook(BookInput bookInput) {
        Book book = new Book(null,
                bookInput.getTitle(),
                bookInput.getPageCount(),
                authorRepository.findById(bookInput.getAuthorId()).orElseThrow());

        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(BookUpdateInput bookUpdateInput) {
        Book bookToUpdate = bookRepository.findById(bookUpdateInput.getBookId()).orElseThrow();
        bookToUpdate.setTitle(bookUpdateInput.getNewTitle());
        return bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
