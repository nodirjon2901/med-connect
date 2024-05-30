package org.example.doctorservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.doctorservice.domain.dto.request.DoctorCreateDTO;
import org.example.doctorservice.domain.dto.response.ApiResponse;
import org.example.doctorservice.domain.dto.response.DoctorResponseDTO;
import org.example.doctorservice.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/findAll/{speciality}")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> findAllBySpeciality(@PathVariable String speciality) {
        return ResponseEntity.ok(doctorService.findBySpeciality(speciality));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(@RequestBody DoctorCreateDTO doctorCreateDTO) {
        return ResponseEntity.ok(doctorService.save(doctorCreateDTO));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> update(
            @RequestBody DoctorCreateDTO doctorCreateDTO,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(doctorService.update(doctorCreateDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

    @PostMapping("/addStack/{doctorId}/{stackId}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> addStackToDoctor(
            @PathVariable Long doctorId,
            @PathVariable Long stackId
    ) {
        return ResponseEntity.ok(doctorService.addStackToDoctor(doctorId, stackId));
    }

}
