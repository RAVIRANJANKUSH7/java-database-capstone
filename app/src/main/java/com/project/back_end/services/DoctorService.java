package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(doctor.getName());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setPassword(doctor.getPassword());
        existingDoctor.setSpeciality(doctor.getSpeciality());
        existingDoctor.setAvailableTime(doctor.getAvailableTime());

        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> searchDoctors(String speciality, String time) {
        if (speciality != null && time != null) {
            return doctorRepository
                    .findBySpecialityAndAvailableTime(speciality, time);
        } else if (speciality != null) {
            return doctorRepository.findBySpeciality(speciality);
        } else {
            return doctorRepository.findAll();
        }
    }

    // Get available time slots for a doctor on a specific date
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {

        Doctor doctor = getDoctorById(doctorId);

        List<String> availableSlots = new ArrayList<>();

        if (doctor.getAvailableTime() != null &&
                !doctor.getAvailableTime().trim().isEmpty()) {
            availableSlots.add(doctor.getAvailableTime());
        }

        return availableSlots;
    }

    // Validate doctor login credentials
    public boolean validateLogin(String email, String password) {

        List<Doctor> doctors = doctorRepository.findAll();

        for (Doctor doctor : doctors) {
            if (doctor.getEmail() != null &&
                    doctor.getPassword() != null &&
                    doctor.getEmail().equals(email) &&
                    doctor.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }
}
