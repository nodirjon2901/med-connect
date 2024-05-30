package org.example.stackservice.feign;

import org.example.stackservice.domain.dto.response.ApiResponse;
import org.example.stackservice.domain.dto.response.DoctorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("DOCTOR-SERVICE")
public interface DoctorFeign {

    @GetMapping("/doctors/findAll/{speciality}")
    ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> findAllBySpeciality(@PathVariable String speciality);

    @PostMapping("/doctors/addStack/{doctorId}/{stackId}")
    ResponseEntity<ApiResponse<DoctorResponseDTO>> addStackToDoctor(
            @PathVariable Long doctorId,
            @PathVariable Long stackId
    );
}
