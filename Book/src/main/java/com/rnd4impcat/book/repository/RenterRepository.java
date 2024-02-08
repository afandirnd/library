package com.rnd4impcat.book.repository;

import com.rnd4impcat.book.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    Optional<Renter> findRenterByPhone(String phone);

    Optional<Renter> findRenterByEmail(String email);

    Optional<Renter> findRenterByName(String name);
}
