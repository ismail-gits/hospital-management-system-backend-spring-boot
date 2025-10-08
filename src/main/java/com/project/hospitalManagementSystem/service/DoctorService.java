package com.project.hospitalManagementSystem.service;

import com.project.hospitalManagementSystem.dto.DoctorResponseDto;
import com.project.hospitalManagementSystem.dto.OnBoardDoctorDto;

import java.util.List;

public interface DoctorService {
  List<DoctorResponseDto> getAllDoctors();

  DoctorResponseDto onBoardDoctor(OnBoardDoctorDto onBoardDoctorDto);
}
