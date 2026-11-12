package com.healthcare.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One: Appointments -> Billing
    @Column(name = "appointment_id", nullable = false, unique = true)
    private Long appointmentId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String status;

    @Column(name = "billing_date")
    private LocalDateTime billingDate;

    @PrePersist
    void onCreate() {
        if (billingDate == null) billingDate = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDateTime billingDate) { this.billingDate = billingDate; }
}
