package com.project.hospitalManagementSystem.controller;

import com.project.hospitalManagementSystem.dto.AppointmentResponseDto;
import com.project.hospitalManagementSystem.dto.CreateAppointmentDto;
import com.project.hospitalManagementSystem.dto.PatientResponseDto;
import com.project.hospitalManagementSystem.service.AppointmentService;
import com.project.hospitalManagementSystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;
  private final AppointmentService appointmentService;

  @PostMapping("/appointments")
  public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentDto createAppointmentDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentDto));
  }

  @GetMapping("/profile")
  public ResponseEntity<PatientResponseDto> getPatientProfile(@RequestBody Long patientId) {
    return ResponseEntity.ok(patientService.getPatientById(patientId));
  }
}
