package com.healthcare.repository;

import com.healthcare.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    boolean existsByAppointmentId(Long appointmentId);
}
