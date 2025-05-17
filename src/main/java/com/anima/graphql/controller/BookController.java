package com.anima.graphql.controller;

import com.anima.graphql.domain.dto.AuthorInput;
import com.anima.graphql.domain.dto.BookInput;
import com.anima.graphql.domain.dto.BookUpdateInput;
import com.anima.graphql.domain.model.Author;
import com.anima.graphql.domain.model.Book;
import com.anima.graphql.service.AuthorService;
import com.anima.graphql.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookService.getBookById(id).orElseThrow();
    }

    @QueryMapping
    public List<Author> authors() {
        return bookService.getAllAuthors();
    }

    @MutationMapping
    public Book addBook(@Argument("input") BookInput bookInput) {
        return bookService.addBook(bookInput);
    }

    @MutationMapping
    public Book updateBook(@Argument("input") BookUpdateInput bookUpdateInput) {
        return bookService.updateBook(bookUpdateInput);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.deleteBook(id);
        return true;
    }

    @MutationMapping
    public Author addAuthor(@Argument("input") AuthorInput authorInput) {
        return authorService.addAuthor(authorInput);
    }

    @QueryMapping
    public LocalDateTime time() {
        return LocalDateTime.now();
    }
}
