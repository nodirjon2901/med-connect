package org.example.mockservice.domain.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.mockservice.domain.model.Doctor;

import java.time.Duration;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorResponse {

    Doctor doctor;

    private String serviceTime;

}
