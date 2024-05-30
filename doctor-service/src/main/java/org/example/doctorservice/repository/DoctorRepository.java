package org.example.doctorservice.repository;

import org.example.doctorservice.domain.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    List<DoctorEntity> findBySpeciality(String speciality);

}
