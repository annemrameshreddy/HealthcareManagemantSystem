package com.healthcare.controller;

import com.healthcare.entity.Doctor;
import com.healthcare.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(doctor));
    }

    @GetMapping
    public List<Doctor> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Doctor getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor doctor) {
        return service.update(id, doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
