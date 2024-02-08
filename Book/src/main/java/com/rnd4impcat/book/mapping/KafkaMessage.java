package com.rnd4impcat.book.mapping;

import java.util.Date;

public class KafkaMessage {

    public static final String TYPE_BOOK_RENTED = "BOOK_RENTED",
                                TYPE_BOOK_RETURNED = "BOOK_RETURNED";

    public KafkaMessage() {}

    public String messageType;
    public String customerName;
    public String customerEmail;
    public String book;
    public Date rentalDate;
    public Date returnDate;
}
