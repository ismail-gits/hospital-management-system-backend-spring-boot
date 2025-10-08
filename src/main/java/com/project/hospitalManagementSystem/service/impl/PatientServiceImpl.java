package com.project.hospitalManagementSystem.service.impl;

import com.project.hospitalManagementSystem.dto.PatientResponseDto;
import com.project.hospitalManagementSystem.entity.Patient;
import com.project.hospitalManagementSystem.repository.PatientRepository;
import com.project.hospitalManagementSystem.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;
  private final ModelMapper modelMapper;

  @Override
  public PatientResponseDto getPatientById(Long id) {
    Patient patient = patientRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No patient found with id: " + id));

    return modelMapper.map(patient, PatientResponseDto.class);
  }

  @Override
  public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
    return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
        .stream()
        .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
        .toList();
  }
}
