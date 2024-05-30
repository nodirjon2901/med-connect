package org.example.stackservice.domain.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.stackservice.enumerators.State;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorResponseDTO {

    Long id;

    String login;

    String password;

    String name;

    String surname;

    String lastName;

    String phoneNumber;

    State doctorState;

    String speciality;

    List<Long> stackId;

    Long polyclinicId;

}
