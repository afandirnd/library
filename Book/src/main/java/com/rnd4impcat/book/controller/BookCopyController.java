package com.rnd4impcat.book.controller;

import com.rnd4impcat.book.entity.BookCopy;
import com.rnd4impcat.book.repository.BookCopyRepository;
import com.rnd4impcat.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/api/v1/books")
@Tag(name = "Book Copy Management", description = "APIs related to Book Copy Management")
public class BookCopyController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @GetMapping(value = "/{id}/bookCopies")
    @Operation(summary = "Get all book copies", description = "Get a list of all book copies")
    public List<BookCopy> getAllBookCopies(@PathVariable long id) {
        return bookCopyRepository.findBookCopiesByBookId(id);
    }

    @GetMapping("/bookCopies/{id}")
    @Operation(summary = "Get book copy by ID", description = "Get a book copy by ID")
    public BookCopy getBookCopyById(@PathVariable Long id) {
        return bookCopyRepository.findById(id).orElse(null);
    }

    @PostMapping(value = "/{id}/bookCopies")
    @Operation(summary = "Create a new book copy", description = "Create a new book copy")
    public BookCopy createBookCopy(@PathVariable long id) {
        return bookService.saveBookCopy(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book copy", description = "Update an existing book copy by ID")
    public BookCopy updateBookCopy(@PathVariable Long id, @RequestBody BookCopy bookCopy) {
        bookCopy.setId(id);
        return bookCopyRepository.save(bookCopy);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book copy by ID", description = "Delete a book copy by ID")
    public void deleteBookCopy(@PathVariable Long id) {
        bookCopyRepository.deleteById(id);
    }
}
