package com.rnd4impcat.book.controller;

import com.rnd4impcat.book.entity.Book;
import com.rnd4impcat.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/api/v1/books")
@Tag(name = "Book Management", description = "APIs related to Book Management")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all books")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Get a book by ID")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findBookById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book", description = "Update an existing book by ID")
    public Book updateBook(long id, @RequestBody Book book) {
        book.setId(id);
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by ID", description = "Delete a book by ID")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
