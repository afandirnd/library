package com.rnd4impcat.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Rental implements Serializable {

    @Serial
    private static final long serialVersionUID = 6558311120516438931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String agentUUID;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private BookCopy copy;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date rentalDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Renter renter;
}
