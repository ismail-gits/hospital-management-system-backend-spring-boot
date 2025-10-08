package com.project.hospitalManagementSystem.service;

import com.project.hospitalManagementSystem.dto.AppointmentResponseDto;
import com.project.hospitalManagementSystem.dto.CreateAppointmentDto;
import com.project.hospitalManagementSystem.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppointmentService {
  List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId);

  AppointmentResponseDto createNewAppointment(CreateAppointmentDto createAppointmentDto);

  AppointmentResponseDto reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId);
}
