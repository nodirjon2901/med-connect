package org.example.stackservice.domain.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.stackservice.enumerators.StackState;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StackResponseDTO {

    Long id;

    Long patientId;

    Long doctorId;

    String doctorSpeciality;

    LocalDateTime time;

    StackState state;

    LocalDateTime createdDateTime;

    LocalDateTime closedDateTime;

}
