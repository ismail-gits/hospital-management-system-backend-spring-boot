package com.project.hospitalManagementSystem.service;

import com.project.hospitalManagementSystem.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {
  PatientResponseDto getPatientById(Long id);

  List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize);
}
