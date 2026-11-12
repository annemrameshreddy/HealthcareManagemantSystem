package com.healthcare.service;

import com.healthcare.entity.Billing;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ConflictException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final BillingRepository repo;
    private final AppointmentRepository appointmentRepo;

    public BillingService(BillingRepository repo, AppointmentRepository appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    public Billing create(Billing b) {
        if (b.getAppointmentId() == null || !appointmentRepo.existsById(b.getAppointmentId()))
            throw new BadRequestException("Valid appointmentId is required");
        if (b.getAmount() == null) throw new BadRequestException("amount is required");
        if (repo.existsByAppointmentId(b.getAppointmentId()))
            throw new ConflictException("A billing entry already exists for this appointment");
        b.setId(null);
        return repo.save(b);
    }

    public List<Billing> findAll() { return repo.findAll(); }

    public Billing findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Billing", id));
    }

    public Billing update(Long id, Billing in) {
        Billing b = findById(id);
        if (in.getAmount() == null) throw new BadRequestException("amount is required");
        // appointment link is fixed (one-to-one); only amount/status/date can change
        b.setAmount(in.getAmount());
        if (in.getStatus() != null) b.setStatus(in.getStatus());
        if (in.getBillingDate() != null) b.setBillingDate(in.getBillingDate());
        return repo.save(b);
    }

    public void delete(Long id) {
        findById(id);
        repo.deleteById(id);
    }
}
