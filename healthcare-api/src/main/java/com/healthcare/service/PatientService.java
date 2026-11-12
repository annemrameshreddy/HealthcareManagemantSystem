package com.healthcare.service;

import com.healthcare.entity.Patient;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ConflictException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.MedicalRecordRepository;
import com.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repo;
    private final AppointmentRepository appointmentRepo;
    private final MedicalRecordRepository recordRepo;

    public PatientService(PatientRepository repo, AppointmentRepository appointmentRepo,
                          MedicalRecordRepository recordRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
        this.recordRepo = recordRepo;
    }

    public Patient create(Patient p) {
        if (p.getName() == null || p.getName().isBlank()) throw new BadRequestException("Patient name is required");
        p.setId(null);
        return repo.save(p);
    }

    public List<Patient> findAll() { return repo.findAll(); }

    public Patient findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    public Patient update(Long id, Patient in) {
        Patient p = findById(id);
        if (in.getName() == null || in.getName().isBlank()) throw new BadRequestException("Patient name is required");
        p.setName(in.getName());
        p.setDob(in.getDob());
        p.setGender(in.getGender());
        p.setContact(in.getContact());
        p.setAddress(in.getAddress());
        return repo.save(p);
    }

    public void delete(Long id) {
        findById(id);
        if (appointmentRepo.existsByPatientId(id) || recordRepo.existsByPatientId(id))
            throw new ConflictException("Patient has appointments or medical records and cannot be deleted");
        repo.deleteById(id);
    }
}
