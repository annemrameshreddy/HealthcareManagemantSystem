package com.healthcare.service;

import com.healthcare.entity.MedicalRecord;
import com.healthcare.exception.BadRequestException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.MedicalRecordRepository;
import com.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repo;
    private final PatientRepository patientRepo;

    public MedicalRecordService(MedicalRecordRepository repo, PatientRepository patientRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
    }

    public MedicalRecord create(MedicalRecord r) {
        if (r.getPatientId() == null || !patientRepo.existsById(r.getPatientId()))
            throw new BadRequestException("Valid patientId is required");
        r.setId(null);
        return repo.save(r);
    }

    /** Optional filter: patientId (patient's medical history). */
    public List<MedicalRecord> findAll(Long patientId) {
        return patientId != null ? repo.findByPatientId(patientId) : repo.findAll();
    }

    public MedicalRecord findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id));
    }

    public MedicalRecord update(Long id, MedicalRecord in) {
        MedicalRecord r = findById(id);
        if (in.getPatientId() == null || !patientRepo.existsById(in.getPatientId()))
            throw new BadRequestException("Valid patientId is required");
        r.setPatientId(in.getPatientId());
        r.setDiagnosis(in.getDiagnosis());
        r.setTreatment(in.getTreatment());
        if (in.getRecordDate() != null) r.setRecordDate(in.getRecordDate());
        return repo.save(r);
    }

    public void delete(Long id) {
        findById(id);
        repo.deleteById(id);
    }
}
