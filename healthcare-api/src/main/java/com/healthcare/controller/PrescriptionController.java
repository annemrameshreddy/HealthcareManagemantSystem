package com.healthcare.controller;

import com.healthcare.entity.Prescription;
import com.healthcare.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Prescription> create(@RequestBody Prescription prescription) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(prescription));
    }

    @GetMapping
    public List<Prescription> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Prescription getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Prescription update(@PathVariable Long id, @RequestBody Prescription prescription) {
        return service.update(id, prescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
