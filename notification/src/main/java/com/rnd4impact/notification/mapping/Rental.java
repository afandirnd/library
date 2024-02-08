package com.rnd4impact.notification.mapping;

import java.util.Date;

public class Rental {
    public Date rentalDate;
    public Renter renter;
    public Copy copy;

    public static class Renter {
        public String name;
        public String email;
    }

    public static class Copy {
        public Book book;
    }

    public static class Book {
        public String isbn;
        public String name;
    }
}
