package com.healthcare.service;

import com.healthcare.entity.Appointment;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ConflictException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.BillingRepository;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.repository.PatientRepository;
import com.healthcare.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final PrescriptionRepository prescriptionRepo;
    private final BillingRepository billingRepo;

    public AppointmentService(AppointmentRepository repo, PatientRepository patientRepo,
                              DoctorRepository doctorRepo, PrescriptionRepository prescriptionRepo,
                              BillingRepository billingRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.prescriptionRepo = prescriptionRepo;
        this.billingRepo = billingRepo;
    }

    public Appointment create(Appointment a) {
        validate(a);
        a.setId(null);
        return repo.save(a);
    }

    /** Optional filters: patientId (patient history) or doctorId (doctor's schedule). */
    public List<Appointment> findAll(Long patientId, Long doctorId) {
        if (patientId != null) return repo.findByPatientId(patientId);
        if (doctorId != null) return repo.findByDoctorId(doctorId);
        return repo.findAll();
    }

    public Appointment findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    }

    public Appointment update(Long id, Appointment in) {
        Appointment a = findById(id);
        validate(in);
        a.setPatientId(in.getPatientId());
        a.setDoctorId(in.getDoctorId());
        a.setAppointmentDate(in.getAppointmentDate());
        a.setReason(in.getReason());
        if (in.getStatus() != null) a.setStatus(in.getStatus());
        return repo.save(a);
    }

    public void delete(Long id) {
        findById(id);
        if (prescriptionRepo.existsByAppointmentId(id) || billingRepo.existsByAppointmentId(id))
            throw new ConflictException("Appointment has a prescription or billing entry; delete those first");
        repo.deleteById(id);
    }

    private void validate(Appointment a) {
        if (a.getPatientId() == null || !patientRepo.existsById(a.getPatientId()))
            throw new BadRequestException("Valid patientId is required");
        if (a.getDoctorId() == null || !doctorRepo.existsById(a.getDoctorId()))
            throw new BadRequestException("Valid doctorId is required");
        if (a.getAppointmentDate() == null)
            throw new BadRequestException("appointmentDate is required");
    }
}
