package org.example.doctorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.doctorservice.domain.dto.request.DoctorCreateDTO;
import org.example.doctorservice.domain.dto.response.ApiResponse;
import org.example.doctorservice.domain.dto.response.DoctorResponseDTO;
import org.example.doctorservice.domain.entity.DoctorEntity;
import org.example.doctorservice.enumerators.State;
import org.example.doctorservice.exception.AlreadyExistsException;
import org.example.doctorservice.exception.DataNotFoundException;
import org.example.doctorservice.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final ModelMapper modelMapper;

    public ApiResponse<DoctorResponseDTO> save(DoctorCreateDTO doctorCreateDTO) {
        DoctorEntity doctorEntity = modelMapper.map(doctorCreateDTO, DoctorEntity.class);
        doctorEntity.setDoctorState(State.HOME);
        doctorEntity.setPolyclinicId(doctorCreateDTO.getPolyclinicId());
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(doctorRepository.save(doctorEntity), DoctorResponseDTO.class);
        return new ApiResponse<>(doctorResponseDTO, "success", 200);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    public ApiResponse<DoctorResponseDTO> update(DoctorCreateDTO doctorCreateDTO, Long id) {
        Optional<DoctorEntity> optionalDoctorEntity = doctorRepository.findById(id);
        if (optionalDoctorEntity.isEmpty()) {
            return new ApiResponse<>(null, "Doctor is not found with this id", 404);
        }
        DoctorEntity doctorEntity = optionalDoctorEntity.get();
        doctorEntity.setLogin(doctorCreateDTO.getLogin());
        doctorEntity.setPassword(doctorCreateDTO.getPassword());
        doctorEntity.setName(doctorCreateDTO.getName());
        doctorEntity.setSurname(doctorCreateDTO.getSurname());
        doctorEntity.setLastName(doctorCreateDTO.getLastName());
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(doctorRepository.save(doctorEntity), DoctorResponseDTO.class);
        return new ApiResponse<>(doctorResponseDTO, "success", 200);
    }

    public ApiResponse<DoctorResponseDTO> findById(Long id) {
        Optional<DoctorEntity> optionalDoctorEntity = doctorRepository.findById(id);
        if (optionalDoctorEntity.isEmpty()) {
            return new ApiResponse<>(null, "Doctor is not found with this id", 404);
        }
        DoctorEntity doctorEntity = optionalDoctorEntity.get();
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(doctorRepository.save(doctorEntity), DoctorResponseDTO.class);
        return new ApiResponse<>(doctorResponseDTO, "success", 200);
    }

    public ApiResponse<List<DoctorResponseDTO>> findAll() {
        List<DoctorResponseDTO> list = doctorRepository.findAll().stream()
                .map(doctorEntity -> {
                    return modelMapper.map(doctorEntity, DoctorResponseDTO.class);
                }).toList();
        return new ApiResponse<>(list, "success", 200);
    }

    public ApiResponse<DoctorResponseDTO> addStackToDoctor(Long doctorId, Long stackId) {
        Optional<DoctorEntity> optionalDoctorEntity = doctorRepository.findById(doctorId);
        if (optionalDoctorEntity.isEmpty()) {
            return new ApiResponse<>(null, "Doctor is not found with this id", 404);
        }
        DoctorEntity doctorEntity = optionalDoctorEntity.get();
        for (Long id : doctorEntity.getStackId()) {
            if (Objects.equals(id, stackId)) {
                return new ApiResponse<>(null, "This stack is already exists in this doctor", 500);
            }
        }
        doctorEntity.getStackId().add(stackId);
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(doctorRepository.save(doctorEntity), DoctorResponseDTO.class);
        return new ApiResponse<>(doctorResponseDTO, "success", 200);
    }

    public ApiResponse<List<DoctorResponseDTO>> findBySpeciality(String speciality) {
        List<DoctorResponseDTO> list = doctorRepository.findBySpeciality(speciality).stream()
                .map(doctorEntity -> {
                    return modelMapper.map(doctorEntity, DoctorResponseDTO.class);
                }).toList();
        return new ApiResponse<>(list, "success", 200);
    }
}
