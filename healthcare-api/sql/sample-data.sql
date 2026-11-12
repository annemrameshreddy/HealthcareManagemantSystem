-- Optional sample data for testing. Run after schema.sql
USE healthcare_db;

INSERT INTO patients (name, dob, gender, contact, address) VALUES
('Ravi Kumar', '1990-05-14', 'Male', '9876543210', 'Kurnool, Andhra Pradesh'),
('Anita Sharma', '1985-11-02', 'Female', '9123456780', 'Hyderabad, Telangana');

INSERT INTO doctors (name, specialization, phone, email) VALUES
('Dr. Suresh Reddy', 'Cardiology', '9988776655', 'suresh.reddy@hospital.com'),
('Dr. Meena Iyer', 'General Medicine', '9811122233', 'meena.iyer@hospital.com');

INSERT INTO appointments (patient_id, doctor_id, appointment_date, reason, status) VALUES
(1, 1, '2026-11-20 10:30:00', 'Chest pain checkup', 'SCHEDULED'),
(2, 2, '2026-11-21 11:00:00', 'Fever and cold', 'SCHEDULED');

INSERT INTO prescriptions (appointment_id, medication, dosage, instructions) VALUES
(1, 'Aspirin', '75 mg once daily', 'Take after breakfast');

INSERT INTO medical_records (patient_id, diagnosis, treatment) VALUES
(1, 'Mild hypertension', 'Lifestyle changes and medication');

INSERT INTO billing (appointment_id, amount, status) VALUES
(1, 1500.00, 'PENDING');
