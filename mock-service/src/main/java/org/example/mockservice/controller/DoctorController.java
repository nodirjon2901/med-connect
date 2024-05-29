package org.example.mockservice.controller;

import org.example.mockservice.domain.model.Appointment;
import org.example.mockservice.domain.model.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    public static List<Doctor> doctors = Arrays.asList(
            new Doctor(1L, "Dr. Alice", "Cardiology", Arrays.asList(
                    new Appointment(1L, 1L, 101L,LocalDateTime.now().plusHours(2)),
                    new Appointment(2L, 1L, 102L,LocalDateTime.now().plusHours(4))
            )),
            new Doctor(2L, "Dr. Bob", "Cardiology", Arrays.asList(
                    new Appointment(3L, 2L, 103L,LocalDateTime.now().plusHours(1)),
                    new Appointment(4L, 2L, 104L,LocalDateTime.now().plusHours(3))
            )),
            new Doctor(3L, "Dr. Charlie", "Dermatology", Arrays.asList(
                    new Appointment(5L, 3L, 105L,LocalDateTime.now().plusHours(5)),
                    new Appointment(6L, 3L, 106L,LocalDateTime.now().plusHours(6))
            ))
    );

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    @GetMapping("/{speciality}")
    public List<Doctor> getDoctorBySpeciality(@PathVariable String speciality) {
        return doctors.stream()
                .filter(doctor -> doctor.getSpecialty().equalsIgnoreCase(speciality))
                .toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id){
        for (Doctor doctor : doctors) {
            if (Objects.equals(doctor.getId(), id)){
                return ResponseEntity.ok(doctor);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
