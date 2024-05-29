package org.example.mockservice.service;

import lombok.RequiredArgsConstructor;
import org.example.mockservice.controller.DoctorController;
import org.example.mockservice.domain.model.Appointment;
import org.example.mockservice.domain.model.Doctor;
import org.example.mockservice.domain.response.DoctorResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final RestTemplate restTemplate;

    private static long appointmentCounter=0;

    public DoctorResponse createAppointment(String speciality,Long clientId) {
        Doctor selectedDoctor = selectBestAvailableDoctor(speciality);
        Duration serviceTime = getServiceTime(selectedDoctor);
        Appointment appointment=new Appointment(
                ++appointmentCounter,
                selectedDoctor.getId(),
                clientId,
                LocalDateTime.now().plus(serviceTime)
        );
        if (selectedDoctor.getAvailableAppointments()==null){
            selectedDoctor.setAvailableAppointments(new ArrayList<>());
        }
        selectedDoctor.getAvailableAppointments().add(appointment);
        return new DoctorResponse(selectedDoctor, formatDuration(serviceTime));
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    private Doctor selectBestAvailableDoctor(String speciality) {
        String url = "http://localhost:8081/api/balance/" + speciality;
        return restTemplate.getForObject(url, Doctor.class);
    }

    private Duration getServiceTime(Doctor doctor) {
        String url = "http://localhost:8081/api/balance/serviceTime";
        Duration duration = restTemplate.postForObject(url, doctor, Duration.class);
        System.out.println(duration);
        return duration;
    }

    public Doctor updateDoctorList(List<Doctor> doctors, Long doctorId, Doctor selectedDoctor){
        for (Doctor doctor : doctors) {
            if (Objects.equals(doctor.getId(), doctorId)){
                doctors.remove(doctor);
                doctors.add(selectedDoctor);
                return selectedDoctor;
            }
        }
        return null;
    }

}
