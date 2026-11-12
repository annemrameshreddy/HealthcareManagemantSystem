package com.healthcare.service;

import com.healthcare.entity.Doctor;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ConflictException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repo;
    private final AppointmentRepository appointmentRepo;

    public DoctorService(DoctorRepository repo, AppointmentRepository appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    public Doctor create(Doctor d) {
        if (d.getName() == null || d.getName().isBlank()) throw new BadRequestException("Doctor name is required");
        d.setId(null);
        return repo.save(d);
    }

    public List<Doctor> findAll() { return repo.findAll(); }

    public Doctor findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    public Doctor update(Long id, Doctor in) {
        Doctor d = findById(id);
        if (in.getName() == null || in.getName().isBlank()) throw new BadRequestException("Doctor name is required");
        d.setName(in.getName());
        d.setSpecialization(in.getSpecialization());
        d.setPhone(in.getPhone());
        d.setEmail(in.getEmail());
        return repo.save(d);
    }

    public void delete(Long id) {
        findById(id);
        if (appointmentRepo.existsByDoctorId(id))
            throw new ConflictException("Doctor has appointments and cannot be deleted");
        repo.deleteById(id);
    }
}
