package org.example.stackservice.domain.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StackCreateDTO {

    Long patientId;

//    Long doctorId;

    String doctorSpeciality;

    LocalDateTime time;

}
