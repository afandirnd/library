package com.rnd4impcat.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd4impcat.book.entity.BookCopy;
import com.rnd4impcat.book.entity.Rental;
import com.rnd4impcat.book.entity.Renter;
import com.rnd4impcat.book.mapping.KafkaMessage;
import com.rnd4impcat.book.repository.BookCopyRepository;
import com.rnd4impcat.book.repository.BookRepository;
import com.rnd4impcat.book.repository.RentalRepository;
import com.rnd4impcat.book.repository.RenterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/book/api/v1/books")
@Tag(name = "Rental Management", description = "APIs related to Rental Management")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private RenterRepository renterRepository;

    private KafkaTemplate<String, String> kafkaTemplate;

    public RentalController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/rentals")
    @Operation(summary = "Get all rentals", description = "Get a list of all rentals")
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @GetMapping("/rentals/{id}")
    @Operation(summary = "Get rental by ID", description = "Get a rental by ID")
    public Rental getRentalById(@PathVariable Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    @PostMapping("/bookCopies/{id}/rentals")
    @Operation(summary = "Create a new rental", description = "Create a new rental")
    public Rental createRental(@PathVariable long id, @RequestBody Rental rental) {
        BookCopy rentedCopy = bookCopyRepository.findById(id).orElseThrow();
        if (!rentedCopy.getStatus().equals(BookCopy.Status.IN_STOCK)) {
            return null;
        }
        rentedCopy.setStatus(BookCopy.Status.RENTED);
        rental.setCopy(rentedCopy);

        if (rental.getRenter() != null) {
            if (rental.getRenter().getId() != null) {
                Renter renter = renterRepository.findById(rental.getRenter().getId()).orElseThrow();
                rental.setRenter(renter);
            }
            else if (rental.getRenter().getPhone() == null || rental.getRenter().getEmail() == null || rental.getRenter().getName() == null) {
                Renter temp = null;
                if (rental.getRenter().getPhone() != null) {
                    temp = renterRepository.findRenterByPhone(rental.getRenter().getPhone()).orElse(null);
                }
                else if (rental.getRenter().getEmail() != null) {
                    temp = renterRepository.findRenterByEmail(rental.getRenter().getEmail()).orElse(null);
                }
                else if (rental.getRenter().getName() != null) {
                    temp = renterRepository.findRenterByName(rental.getRenter().getName()).orElse(null);
                }
                if (temp != null) {
                    rental.setRenter(temp);
                }
            }
        }

        Rental temp = rentalRepository.save(rental);

        KafkaMessage message = new KafkaMessage();
        message.customerName = rental.getRenter().getName();
        message.customerEmail = rental.getRenter().getEmail();
        message.messageType = KafkaMessage.TYPE_BOOK_RENTED;
        message.book = temp.getCopy().getBook().getName();
        message.rentalDate = rental.getRentalDate();
        try {
            kafkaTemplate.send("notifications", new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    @PutMapping("/bookCopies/rentals/{id}/return")
    @Operation(summary = "Return an existing rental", description = "Renter has returned the book copy they rented")
    public Rental returnRental(@PathVariable Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        if (rental.getCopy().getStatus() != BookCopy.Status.RENTED) {
            return null;
        }
        rental.getCopy().setStatus(BookCopy.Status.IN_STOCK);
        rental.setReturnDate(new Date());
        Rental temp = rentalRepository.save(rental);

        KafkaMessage message = new KafkaMessage();
        message.customerName = rental.getRenter().getName();
        message.customerEmail = rental.getRenter().getEmail();
        message.messageType = KafkaMessage.TYPE_BOOK_RETURNED;
        message.book = temp.getCopy().getBook().getName();
        message.rentalDate = rental.getRentalDate();
        message.returnDate = rental.getReturnDate();
        try {
            kafkaTemplate.send("notifications", new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return temp;
    }

    @GetMapping ("/bookCopies/rentals/overdue")
    @Operation(summary = "Get all overdue rentals", description = "Query all rentals which has no return date and rental return is overdue")
    public List<Rental> getOverdueRentals() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        return rentalRepository.findRentalsByReturnDateIsNullAndRentalDateIsBeforeAndCopyStatus(calendar.getTime(), BookCopy.Status.RENTED);
    }

    @PutMapping("/bookCopies/rentals/{id}")
    @Operation(summary = "Update an existing rental", description = "Update an existing rental by ID")
    public Rental updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        rental.setId(id);
        return rentalRepository.save(rental);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a rental by ID", description = "Delete a rental by ID")
    public void deleteRental(@PathVariable Long id) {
        rentalRepository.deleteById(id);
    }
}
