package org.example.balancingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.balancingservice.model.response.Doctor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalancingService {

    private final RestTemplate restTemplate;

    public Doctor selectBestAvailableDoctor(String speciality) {
        List<Doctor> doctors = getDoctorsBySpeciality(speciality);
        return doctors.stream()
                .min(Comparator.comparing(doctor -> getNextAvailableTime(doctor)))
                .orElseThrow(() -> new RuntimeException("No available doctors found"));
    }

    private List<Doctor> getDoctorsBySpeciality(String speciality) {
        String url = "http://localhost:8080/api/doctors/" + speciality;
        Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);
        return List.of(doctors);
    }

    public LocalDateTime getNextAvailableTime(Doctor doctor) {
        return doctor.getAvailableAppointments().stream()
                .map(appointment -> {
                    return appointment.getAppointmentDate();
                })
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
    }

    public Duration getServiceTime(Doctor doctor) {
        LocalDateTime nextAvailableTime = getNextAvailableTime(doctor);
        Duration duration = Duration.between(LocalDateTime.now(), nextAvailableTime);
        return duration;
    }

//    private String formatDuration(Duration duration) {
//        long hours = duration.toHours();
//        long minutes = duration.toMinutes() % 60;
//        long seconds = duration.getSeconds() % 60;
//        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
//    }

}
