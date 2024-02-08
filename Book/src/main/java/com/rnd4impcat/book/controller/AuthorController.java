package com.rnd4impcat.book.controller;

import com.rnd4impcat.book.entity.Author;
import com.rnd4impcat.book.repository.AuthorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Author Management", description = "APIs related to Author Management")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    @Operation(summary = "Get all authors", description = "Get a list of all authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by ID", description = "Get an author by ID")
    public Author getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Create a new author", description = "Create a new author")
    public Author createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing author", description = "Update an existing author by ID")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        author.setId(id);
        return authorRepository.save(author);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author by ID", description = "Delete an author by ID")
    public void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }
}
