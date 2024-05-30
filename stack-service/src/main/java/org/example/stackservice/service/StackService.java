package org.example.stackservice.service;

import org.example.stackservice.domain.dto.request.StackCreateDTO;
import org.example.stackservice.domain.dto.response.ApiResponse;
import org.example.stackservice.domain.dto.response.DoctorResponseDTO;
import org.example.stackservice.domain.dto.response.StackResponseDTO;
import org.example.stackservice.domain.entity.StackEntity;
import org.example.stackservice.enumerators.StackState;
import org.example.stackservice.exception.DataNotFoundException;
import org.example.stackservice.feign.DoctorFeign;
import org.example.stackservice.repository.StackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StackService {

    @Autowired
    StackRepository stackRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DoctorFeign doctorInterface;

    public ApiResponse<StackResponseDTO> save(StackCreateDTO stackCreateDTO) {
        StackEntity stackEntity = modelMapper.map(stackCreateDTO, StackEntity.class);
        DoctorResponseDTO doctorResponseDTO = selectBestAvailableDoctor(stackCreateDTO.getDoctorSpeciality()).getObject();
        stackEntity.setState(StackState.PENDING);
        stackEntity.setCreatedDateTime(getServiceTime(doctorResponseDTO));
        stackEntity.setDoctorId(doctorResponseDTO.getId());
        StackResponseDTO stackResponseDTO = modelMapper.map(stackRepository.save(stackEntity), StackResponseDTO.class);
        doctorInterface.addStackToDoctor(stackEntity.getDoctorId(), stackEntity.getId());
        return new ApiResponse<>(stackResponseDTO, "Success", 200);
    }

    public void delete(Long id) {
        stackRepository.deleteById(id);
    }

    public ApiResponse<StackResponseDTO> update(StackCreateDTO stackCreateDTO, Long id) {
        Optional<StackEntity> optionalStackEntity = stackRepository.findById(id);

        if (optionalStackEntity.isEmpty()) {
            return new ApiResponse<>(null, "Stack is not found with this id", 404);
        }

        StackEntity stackEntity = optionalStackEntity.get();
        stackEntity.setTime(stackCreateDTO.getTime());

        StackResponseDTO stackResponseDTO = modelMapper.map(stackRepository.save(stackEntity), StackResponseDTO.class);
        return new ApiResponse<>(stackResponseDTO, "success", 200);
    }

    public ApiResponse<StackResponseDTO> findById(Long id) {
        Optional<StackEntity> optionalStackEntity = stackRepository.findById(id);
        if (optionalStackEntity.isEmpty()) {
            return new ApiResponse<>(null, "Stack is not found with this id", 404);
        }
        StackResponseDTO stackResponseDTO = modelMapper.map(optionalStackEntity.get(), StackResponseDTO.class);
        return new ApiResponse<>(stackResponseDTO, "success", 200);
    }

    public ApiResponse<List<StackResponseDTO>> findAll() {
        List<StackResponseDTO> list = stackRepository.findAll().stream()
                .map(stackEntity -> {
                    return modelMapper.map(stackEntity, StackResponseDTO.class);
                }).toList();
        return new ApiResponse<>(list, "success", 200);
    }

//    Stackdagi specialityga to'g'ri keladigan eng muqobil doctorni tanlash jarayoni

    public ApiResponse<DoctorResponseDTO> selectBestAvailableDoctor(String speciality) {
        List<DoctorResponseDTO> doctorResponses = findBySpeciality(speciality);
        System.out.println(doctorResponses);
        DoctorResponseDTO responseDTO = doctorResponses.stream()
                .min(Comparator.comparing(this::getNextAvailableTime))
                .orElseThrow(() -> new DataNotFoundException("No available doctors found"));
        System.out.println(responseDTO);
        return new ApiResponse<>(responseDTO, "success", 200);
    }

    private List<StackEntity> findDoctorsStack(Long doctorId) {
        return stackRepository.findByDoctorId(doctorId);
    }

    private List<DoctorResponseDTO> findBySpeciality(String speciality) {
        return Objects.requireNonNull(doctorInterface.findAllBySpeciality(speciality).getBody()).getObject();
    }

    private LocalDateTime getNextAvailableTime(DoctorResponseDTO doctor) {
        List<StackEntity> stacks = findDoctorsStack(doctor.getId());
        if (stacks.isEmpty()) {
            return LocalDateTime.MIN; // Bo'sh stacklar uchun eng muqobil vaqtni qaytarish
        }
        return stacks.stream()
                .map(StackEntity::getTime)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MIN); // Stack bo'sh bo'lsa eng kam vaqtni qaytarish
    }


    public LocalDateTime getServiceTime(DoctorResponseDTO doctorResponseDTO) {
        LocalDateTime nextAvailableTime = getNextAvailableTime(doctorResponseDTO);
        Duration duration = Duration.between(LocalDateTime.now(), nextAvailableTime);
        return LocalDateTime.now().plus(duration);
    }

}
