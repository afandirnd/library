package com.rnd4impcat.book.controller;

import com.rnd4impcat.book.entity.Renter;
import com.rnd4impcat.book.repository.RenterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/renters")
@Tag(name = "Renter Management", description = "APIs related to Renter Management")
public class RenterController {

    @Autowired
    private RenterRepository renterRepository;

    @GetMapping
    @Operation(summary = "Get all renters", description = "Get a list of all renters")
    public List<Renter> getAllRenters() {
        return renterRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get renter by ID", description = "Get a renter by ID")
    public Renter getRenterById(@PathVariable Long id) {
        return renterRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Create a new renter", description = "Create a new renter")
    public Renter createRenter(@RequestBody Renter renter) {
        return renterRepository.save(renter);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing renter", description = "Update an existing renter by ID")
    public Renter updateRenter(@PathVariable Long id, @RequestBody Renter renter) {
        renter.setId(id);
        return renterRepository.save(renter);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a renter by ID", description = "Delete a renter by ID")
    public void deleteRenter(@PathVariable Long id) {
        renterRepository.deleteById(id);
    }
}
