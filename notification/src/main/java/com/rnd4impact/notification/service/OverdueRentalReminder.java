package com.rnd4impact.notification.service;

import com.rnd4impact.notification.mapping.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class OverdueRentalReminder {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled (cron = "0 0 12 * * *")
    public void collectAndRemind() {
        Rental[] overdueRentals = restTemplate.getForObject("http://BOOK/book/api/v1/rentals/overdue", Rental[].class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (overdueRentals == null) return;

        for (Rental rental : overdueRentals) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("library@rnd4impact.com");
            msg.setTo(rental.renter.email);
            msg.setSubject("Overdue rental reminder");
            msg.setText(String.format("Dear %s, \n\nThis is a kind reminder that the book \"%s\" isbn \"%s\" you rented on %s is now due return. Please visit the library to return the book copy. \n\nThanks.",
                rental.renter.name,
                rental.copy.book.name,
                rental.copy.book.isbn,
                dateFormat.format(rental.rentalDate)
            ));
            emailSender.send(msg);
        }
    }
}
