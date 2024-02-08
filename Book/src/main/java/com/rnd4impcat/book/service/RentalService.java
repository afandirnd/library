package com.rnd4impcat.book.service;

import com.rnd4impcat.book.entity.Rental;
import com.rnd4impcat.book.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    private RentalRepository repository;

    public Rental createRental(Rental rental) {
        return repository.save(rental);
    }
}
