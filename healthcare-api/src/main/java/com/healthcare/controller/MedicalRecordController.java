package com.healthcare.controller;

import com.healthcare.entity.MedicalRecord;
import com.healthcare.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<MedicalRecord> create(@RequestBody MedicalRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(record));
    }

    // Optional filter: ?patientId=1 (patient's medical history)
    @GetMapping
    public List<MedicalRecord> getAll(@RequestParam(required = false) Long patientId) {
        return service.findAll(patientId);
    }

    @GetMapping("/{id}")
    public MedicalRecord getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public MedicalRecord update(@PathVariable Long id, @RequestBody MedicalRecord record) {
        return service.update(id, record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
