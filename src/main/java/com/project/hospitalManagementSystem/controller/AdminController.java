package com.project.hospitalManagementSystem.controller;

import com.project.hospitalManagementSystem.dto.DoctorResponseDto;
import com.project.hospitalManagementSystem.dto.OnBoardDoctorDto;
import com.project.hospitalManagementSystem.dto.PatientResponseDto;
import com.project.hospitalManagementSystem.service.DoctorService;
import com.project.hospitalManagementSystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final PatientService patientService;
  private final DoctorService doctorService;

  @GetMapping("/patients")
  public ResponseEntity<List<PatientResponseDto>> getAllPatients(
      @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
      @RequestParam(value = "size", defaultValue = "10") Integer pageSize
  ) {
    return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
  }

  @PostMapping("/onBoardNewDoctor")
  public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnBoardDoctorDto onBoardDoctorDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardDoctor(onBoardDoctorDto));
  }
}
