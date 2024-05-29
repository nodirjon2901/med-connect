package org.example.doctorservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.doctorservice.enumerators.State;

import java.util.List;

@Entity(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String login;

    String password;

    @Column(length = 50)
    String name;

    @Column(length = 50)
    String surname;

    @Column(length = 50)
    String lastName;

    @Column(length = 20)
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    State doctorState;

    @Column(length = 50)
    String speciality;

    List<Long> stackId;

    Long polyclinicId;

}
