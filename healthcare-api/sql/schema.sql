-- Healthcare database schema (MySQL 8+)
-- Run:  mysql -u root -p < sql/schema.sql

CREATE DATABASE IF NOT EXISTS healthcare_db;
USE healthcare_db;

CREATE TABLE IF NOT EXISTS patients (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    dob         DATE,
    gender      VARCHAR(20),
    contact     VARCHAR(50),
    address     VARCHAR(500),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctors (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    specialization  VARCHAR(255),
    phone           VARCHAR(50),
    email           VARCHAR(255)
);

-- Patients 1 -> N Appointments, Doctors 1 -> N Appointments
CREATE TABLE IF NOT EXISTS appointments (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id          BIGINT NOT NULL,
    doctor_id           BIGINT NOT NULL,
    appointment_date    DATETIME NOT NULL,
    reason              VARCHAR(500),
    status              VARCHAR(50),
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appointment_doctor  FOREIGN KEY (doctor_id)  REFERENCES doctors(id)
);

-- Appointments 1 -> 1 Prescriptions
CREATE TABLE IF NOT EXISTS prescriptions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id  BIGINT NOT NULL UNIQUE,
    medication      VARCHAR(255),
    dosage          VARCHAR(255),
    instructions    VARCHAR(1000),
    CONSTRAINT fk_prescription_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

-- Patients 1 -> N MedicalRecords
CREATE TABLE IF NOT EXISTS medical_records (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id   BIGINT NOT NULL,
    diagnosis    VARCHAR(500),
    treatment    VARCHAR(1000),
    record_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_record_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Appointments 1 -> 1 Billing
CREATE TABLE IF NOT EXISTS billing (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id  BIGINT NOT NULL UNIQUE,
    amount          DECIMAL(10,2) NOT NULL,
    status          VARCHAR(50),
    billing_date    DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_billing_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
