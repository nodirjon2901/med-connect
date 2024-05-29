package org.example.doctorservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.doctorservice.domain.dto.request.DoctorCreateDTO;
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
    public ResponseEntity<List<DoctorResponseDTO>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@RequestBody DoctorCreateDTO doctorCreateDTO) {
        doctorService.save(doctorCreateDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DoctorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorResponseDTO> update(
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

}
