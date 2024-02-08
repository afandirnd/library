package com.rnd4impcat.book.service;

import com.rnd4impcat.book.entity.Author;
import com.rnd4impcat.book.entity.Book;
import com.rnd4impcat.book.entity.BookCopy;
import com.rnd4impcat.book.repository.AuthorRepository;
import com.rnd4impcat.book.repository.BookCopyRepository;
import com.rnd4impcat.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> findBookById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        if (book.getAuthor().getId() <= 0) {
            authorRepository.save(book.getAuthor());
        }
        return repository.save(book);
    }

    public Book update(Book book) {
        return repository.save(book);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public BookCopy saveBookCopy(long id) {
        Book book = repository.findById(id).orElseThrow();
        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setStatus(BookCopy.Status.IN_STOCK);
        return bookCopyRepository.save(copy);
    }
}
