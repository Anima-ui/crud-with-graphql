package com.anima.graphql.controller;

import com.anima.graphql.domain.model.Author;
import com.anima.graphql.domain.model.Book;
import com.anima.graphql.repository.AuthorRepository;
import com.anima.graphql.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
class BookControllerTest {

    private final GraphQlTester graphQlTester;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private HttpGraphQlTester httpGraphQlTester;
    private final WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        Author author = authorRepository.save(new Author(null, "John Doe"));
        bookRepository.save(new Book(null, "1984", 43, author));

        WebTestClient client = MockMvcWebTestClient.bindToApplicationContext(webApplicationContext)
                .configureClient()
                .baseUrl("/graphql")
                .build();

        httpGraphQlTester = HttpGraphQlTester.create(client);
    }

    @Autowired
    public BookControllerTest(GraphQlTester graphQlTester, BookRepository bookRepository,
                              AuthorRepository authorRepository, WebApplicationContext webApplicationContext) {
        this.graphQlTester = graphQlTester;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.webApplicationContext = webApplicationContext;
    }

    @Test
    void booksTest() {
        graphQlTester
                .documentName("books")
                .operationName("books")
                .execute()
                .path("books")
                .entityList(Book.class)
                .hasSizeGreaterThan(0);
    }

    @Test
    @Transactional
    void addAuthorTest() {
        Author author = httpGraphQlTester
                .documentName("books")
                .operationName("addAuthor")
                .execute()
                .path("addAuthor")
                .entity(Author.class)
                .get();

        Assertions.assertEquals("John", author.getName());
        Assertions.assertNotNull(author.getId());
    }
}