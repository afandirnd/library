package com.rnd4impcat.book.repository;

import com.rnd4impcat.book.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findBookCopiesByBookId(long bookId);
}
