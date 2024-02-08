package com.rnd4impcat.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = -4166999772790700157L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String isbn;

    @ManyToOne
    private Author author;

    private Date pubDate;

    private String edition;
}
