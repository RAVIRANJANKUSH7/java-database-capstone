package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private Patient patient;

    @NotNull
    @Future
    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    // Default constructor
    public Appointment() {
    }

    // Parameterized constructor
    public Appointment(Doctor doctor, Patient patient,
                       LocalDateTime appointmentTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentTime = appointmentTime;
    }

    // Get ID
    public Long getId() {
        return id;
    }

    // Set ID
    public void setId(Long id) {
        this.id = id;
    }

    // Get Doctor
    public Doctor getDoctor() {
        return doctor;
    }

    // Set Doctor
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // Get Patient
    public Patient getPatient() {
        return patient;
    }

    // Set Patient
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Get Appointment Time
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    // Set Appointment Time
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", appointmentTime=" + appointmentTime +
                '}';
    }
}
