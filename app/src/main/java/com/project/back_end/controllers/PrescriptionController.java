package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByPatient(patientId)
        );
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(
            @RequestBody Prescription prescription) {
        return ResponseEntity.ok(
                prescriptionService.createPrescription(prescription)
        );
    }
}
