package com.anima.graphql.service;

import com.anima.graphql.domain.dto.AuthorInput;
import com.anima.graphql.domain.model.Author;
import com.anima.graphql.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(AuthorInput authorInput) {
        Author author = new Author(null, authorInput.getName());
        return authorRepository.save(author);
    }
}
