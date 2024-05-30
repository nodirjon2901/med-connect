package org.example.stackservice.repository;

import org.example.stackservice.domain.entity.StackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StackRepository extends JpaRepository<StackEntity, Long> {

    List<StackEntity> findByDoctorId(Long id);

}
