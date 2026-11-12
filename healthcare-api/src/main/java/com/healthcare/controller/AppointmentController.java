package com.healthcare.controller;

import com.healthcare.entity.Appointment;
import com.healthcare.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(appointment));
    }

    // Optional filters: ?patientId=1 or ?doctorId=1
    @GetMapping
    public List<Appointment> getAll(@RequestParam(required = false) Long patientId,
                                    @RequestParam(required = false) Long doctorId) {
        return service.findAll(patientId, doctorId);
    }

    @GetMapping("/{id}")
    public Appointment getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return service.update(id, appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
