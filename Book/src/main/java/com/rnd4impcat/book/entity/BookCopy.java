package com.rnd4impcat.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class BookCopy implements Serializable {

    public enum Status { IN_STOCK, LOST, RENTED }

    @Serial
    private static final long serialVersionUID = -8307989083171820409L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
}
