package org.example.doctorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.doctorservice.domain.dto.request.DoctorCreateDTO;
import org.example.doctorservice.domain.dto.response.DoctorResponseDTO;
import org.example.doctorservice.domain.entity.DoctorEntity;
import org.example.doctorservice.enumerators.State;
import org.example.doctorservice.exception.DataNotFoundException;
import org.example.doctorservice.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements BaseService<DoctorResponseDTO, DoctorCreateDTO> {

    private final DoctorRepository doctorRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(DoctorCreateDTO doctorCreateDTO) {
        DoctorEntity doctorEntity = modelMapper.map(doctorCreateDTO, DoctorEntity.class);
        doctorEntity.setDoctorState(State.HOME);
        doctorEntity.setPolyclinicId(doctorCreateDTO.getPolyclinicId());
        doctorRepository.save(doctorEntity);

    }

    @Override
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public DoctorResponseDTO update(DoctorCreateDTO doctorCreateDTO, Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Doctor is not found with this id"));
        doctorEntity.setLogin(doctorCreateDTO.getLogin());
        doctorEntity.setPassword(doctorCreateDTO.getPassword());
        doctorEntity.setName(doctorCreateDTO.getName());
        doctorEntity.setSurname(doctorCreateDTO.getSurname());
        doctorEntity.setLastName(doctorCreateDTO.getLastName());
        return modelMapper.map(doctorRepository.save(doctorEntity), DoctorResponseDTO.class);
    }

    @Override
    public DoctorResponseDTO findById(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Doctor is not found with this id"));
        return modelMapper.map(doctorEntity, DoctorResponseDTO.class);
    }

    @Override
    public List<DoctorResponseDTO> findAll() {
        return doctorRepository.findAll().stream()
                .map(doctorEntity -> {
                    return modelMapper.map(doctorEntity, DoctorResponseDTO.class);
                }).toList();
    }
}
