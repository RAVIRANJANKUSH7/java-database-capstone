# Smart Clinic Management System - MySQL Database Design

## Overview

The Smart Clinic Management System uses a MySQL relational database to manage doctors, patients, appointments, prescriptions, and administrators.

## Database Name

`smart_clinic_db`

## Tables

### 1. Doctor Table

Stores information about doctors registered in the clinic management system.

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| doctor_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for a doctor |
| name | VARCHAR(100) | NOT NULL | Doctor's full name |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Doctor's email address |
| password | VARCHAR(255) | NOT NULL | Doctor's encrypted password |
| speciality | VARCHAR(100) | NOT NULL | Doctor's medical speciality |
| available_time | VARCHAR(100) | | Doctor's available consultation time |

### 2. Patient Table

Stores information about patients registered in the system.

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| patient_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for a patient |
| name | VARCHAR(100) | NOT NULL | Patient's full name |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Patient's email address |
| password | VARCHAR(255) | NOT NULL | Patient's encrypted password |
| phone | VARCHAR(20) | | Patient's contact number |
| date_of_birth | DATE | | Patient's date of birth |
| gender | VARCHAR(20) | | Patient's gender |

### 3. Admin Table

Stores administrator account information.

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| admin_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for an administrator |
| name | VARCHAR(100) | NOT NULL | Administrator's name |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Administrator's email |
| password | VARCHAR(255) | NOT NULL | Administrator's encrypted password |

### 4. Appointment Table

Stores appointments booked by patients with doctors.

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| appointment_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique appointment identifier |
| doctor_id | BIGINT | FOREIGN KEY, NOT NULL | References Doctor table |
| patient_id | BIGINT | FOREIGN KEY, NOT NULL | References Patient table |
| appointment_date | DATE | NOT NULL | Date of appointment |
| appointment_time | TIME | NOT NULL | Time of appointment |
| status | VARCHAR(50) | NOT NULL | Appointment status |
| reason | VARCHAR(255) | | Reason for the appointment |

### 5. Prescription Table

Stores prescriptions issued by doctors to patients.

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| prescription_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique prescription identifier |
| appointment_id | BIGINT | FOREIGN KEY, NOT NULL | References Appointment table |
| doctor_id | BIGINT | FOREIGN KEY, NOT NULL | References Doctor table |
| patient_id | BIGINT | FOREIGN KEY, NOT NULL | References Patient table |
| medication | VARCHAR(255) | NOT NULL | Prescribed medication |
| dosage | VARCHAR(100) | | Medication dosage |
| instructions | TEXT | | Instructions for the patient |
| prescription_date | DATE | NOT NULL | Date prescription was issued |

## Relationships

- One Doctor can have many Appointments.
- One Patient can book many Appointments.
- Each Appointment belongs to one Doctor and one Patient.
- One Doctor can issue many Prescriptions.
- One Patient can receive many Prescriptions.
- A Prescription is associated with an Appointment.

## Entity Relationship Summary

Doctor (1) ---- (M) Appointment (M) ---- (1) Patient

Doctor (1) ---- (M) Prescription (M) ---- (1) Patient

Appointment (1) ---- (M) Prescription

## MySQL Schema

~~~sql
CREATE DATABASE IF NOT EXISTS smart_clinic_db;

USE smart_clinic_db;

CREATE TABLE Doctor (
    doctor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    available_time VARCHAR(100)
);

CREATE TABLE Patient (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(20)
);

CREATE TABLE Admin (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Appointment (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    reason VARCHAR(255),
    CONSTRAINT fk_appointment_doctor
        FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id),
    CONSTRAINT fk_appointment_patient
        FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);

CREATE TABLE Prescription (
    prescription_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    medication VARCHAR(255) NOT NULL,
    dosage VARCHAR(100),
    instructions TEXT,
    prescription_date DATE NOT NULL,
    CONSTRAINT fk_prescription_appointment
        FOREIGN KEY (appointment_id) REFERENCES Appointment(appointment_id),
    CONSTRAINT fk_prescription_doctor
        FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id),
    CONSTRAINT fk_prescription_patient
        FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);
~~~

## Stored Procedures

The Smart Clinic Management System includes the following stored procedures for generating appointment and doctor reports:

1. `GetDailyAppointmentReportByDoctor` - Generates a daily appointment report grouped by doctor.
2. `GetDoctorWithMostPatientsByMonth` - Identifies the doctor who attended the most patients during a specified month.
3. `GetDoctorWithMostPatientsByYear` - Identifies the doctor who attended the most patients during a specified year.
