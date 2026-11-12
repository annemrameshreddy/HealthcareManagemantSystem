package com.healthcare.controller;

import com.healthcare.entity.Billing;
import com.healthcare.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Billing> create(@RequestBody Billing billing) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(billing));
    }

    @GetMapping
    public List<Billing> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Billing getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Billing update(@PathVariable Long id, @RequestBody Billing billing) {
        return service.update(id, billing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
