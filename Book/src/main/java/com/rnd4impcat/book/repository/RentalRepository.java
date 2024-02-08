package com.rnd4impcat.book.repository;

import com.rnd4impcat.book.entity.BookCopy;
import com.rnd4impcat.book.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findRentalsByReturnDateIsNullAndRentalDateIsBeforeAndCopyStatus(Date due, BookCopy.Status status);
}
