package com.rnd4impcat.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Renter implements Serializable {

    @Serial
    private static final long serialVersionUID = -1970238995407954084L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 10, max = 10)
    private String phone;

    @Email
    @Column(unique = true, nullable = false)
    private String email;
}
