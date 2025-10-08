package com.project.hospitalManagementSystem.controller;

import com.project.hospitalManagementSystem.dto.AppointmentResponseDto;
import com.project.hospitalManagementSystem.entity.User;
import com.project.hospitalManagementSystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

  private final AppointmentService appointmentService;

  public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
  }
}
