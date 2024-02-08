package com.rnd4impact.notification.mapping;

import java.util.Date;

public class Message {

    public enum Type {
        BOOK_RENTED,
        BOOK_RETURNED
    }

    public Message() {}

    public Type messageType;
    public String customerName;
    public String customerEmail;
    public String book;
    public Date rentalDate;
    public Date returnDate;
}
