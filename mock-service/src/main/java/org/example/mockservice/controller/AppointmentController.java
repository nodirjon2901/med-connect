package org.example.mockservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.mockservice.domain.response.DoctorResponse;
import org.example.mockservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/{clientId}")
    public ResponseEntity<DoctorResponse> createAppointment(
            @RequestParam String speciality,
            @PathVariable Long clientId
            ){
        return ResponseEntity.ok(appointmentService.createAppointment(speciality,clientId));
    }

}
