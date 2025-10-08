package com.project.hospitalManagementSystem.service.impl;

import com.project.hospitalManagementSystem.dto.DoctorResponseDto;
import com.project.hospitalManagementSystem.dto.OnBoardDoctorDto;
import com.project.hospitalManagementSystem.entity.Doctor;
import com.project.hospitalManagementSystem.entity.User;
import com.project.hospitalManagementSystem.entity.type.RoleType;
import com.project.hospitalManagementSystem.repository.DoctorRepository;
import com.project.hospitalManagementSystem.repository.UserRepository;
import com.project.hospitalManagementSystem.service.DoctorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

  private final DoctorRepository doctorRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<DoctorResponseDto> getAllDoctors() {
    return doctorRepository.findAll()
        .stream()
        .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
        .toList();
  }

  @Override
  @Transactional
  public DoctorResponseDto onBoardDoctor(OnBoardDoctorDto onBoardDoctorDto) {
    User user = userRepository
        .findById(onBoardDoctorDto.getUserId())
        .orElseThrow();

    if (doctorRepository.existsById(onBoardDoctorDto.getUserId())) {
      throw new IllegalArgumentException("Already a doctor");
    }

    Doctor doctor = Doctor.builder()
        .name(onBoardDoctorDto.getName())
        .email(user.getUsername())
        .specialization(onBoardDoctorDto.getSpecialization())
        .user(user)
        .build();

    user.getRoles().add(RoleType.DOCTOR);

    return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
  }
}
