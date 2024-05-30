package org.example.stackservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.stackservice.enumerators.StackState;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "stacks")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class StackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long patientId;

    Long doctorId;

    String doctorSpeciality;

    LocalDateTime time;

    @Enumerated(EnumType.STRING)
    StackState state;

    @CreationTimestamp
    LocalDateTime createdDateTime;

    LocalDateTime closedDateTime;


}
