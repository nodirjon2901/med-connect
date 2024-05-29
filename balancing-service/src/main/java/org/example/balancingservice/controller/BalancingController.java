package org.example.balancingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.balancingservice.model.response.Doctor;
import org.example.balancingservice.service.BalancingService;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalancingController {

    private final BalancingService balancingService;

    @GetMapping("/{speciality}")
    public Doctor selectBestAvailableDoctor(@PathVariable String speciality) {
        return balancingService.selectBestAvailableDoctor(speciality);
    }

    @PostMapping("/serviceTime")
    public Duration getServiceTime(@RequestBody Doctor doctor) {
        return balancingService.getServiceTime(doctor);
    }
}
