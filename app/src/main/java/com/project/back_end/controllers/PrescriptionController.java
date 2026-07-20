package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Create a new prescription with token
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> createPrescription(
            @Valid @RequestBody Prescription prescription,
            @PathVariable String token) {

        Map<String, String> response = new HashMap<>();

        // Validate token
        if (token == null || token.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Invalid or missing token");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        try {
            prescriptionService.createPrescription(prescription);

            response.put("status", "success");
            response.put("message", "Prescription created successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to create prescription");

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
