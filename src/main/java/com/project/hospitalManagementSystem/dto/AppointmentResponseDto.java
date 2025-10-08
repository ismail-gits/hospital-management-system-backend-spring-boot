package com.project.hospitalManagementSystem.dto;

import com.project.hospitalManagementSystem.entity.type.AppointmentStatusType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {

  private Long id;

  private LocalDateTime appointmentTime;

  private String reason;

  private AppointmentStatusType status;

  private DoctorResponseDto doctor;
}
