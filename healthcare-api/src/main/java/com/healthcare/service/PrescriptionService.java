package com.healthcare.service;

import com.healthcare.entity.Prescription;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ConflictException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repo;
    private final AppointmentRepository appointmentRepo;

    public PrescriptionService(PrescriptionRepository repo, AppointmentRepository appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    public Prescription create(Prescription p) {
        if (p.getAppointmentId() == null || !appointmentRepo.existsById(p.getAppointmentId()))
            throw new BadRequestException("Valid appointmentId is required");
        if (repo.existsByAppointmentId(p.getAppointmentId()))
            throw new ConflictException("A prescription already exists for this appointment");
        p.setId(null);
        return repo.save(p);
    }

    public List<Prescription> findAll() { return repo.findAll(); }

    public Prescription findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription", id));
    }

    public Prescription update(Long id, Prescription in) {
        Prescription p = findById(id);
        // appointment link is fixed (one-to-one); only the content can change
        p.setMedication(in.getMedication());
        p.setDosage(in.getDosage());
        p.setInstructions(in.getInstructions());
        return repo.save(p);
    }

    public void delete(Long id) {
        findById(id);
        repo.deleteById(id);
    }
}
