package org.example.stackservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.stackservice.domain.dto.request.StackCreateDTO;
import org.example.stackservice.domain.dto.response.ApiResponse;
import org.example.stackservice.domain.dto.response.DoctorResponseDTO;
import org.example.stackservice.domain.dto.response.StackResponseDTO;
import org.example.stackservice.service.StackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {

    private final StackService stackService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StackResponseDTO>> create(@RequestBody StackCreateDTO stackCreateDTO) {
        return ResponseEntity.ok(stackService.save(stackCreateDTO));
    }

    @GetMapping("/getBestAvailableDoctor/{speciality}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getBestAvailableDoctor(@PathVariable String speciality) {
        return ResponseEntity.ok(stackService.selectBestAvailableDoctor(speciality));
    }

}
