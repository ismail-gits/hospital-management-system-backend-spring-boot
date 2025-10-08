package com.project.hospitalManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentDto {

  private Long doctorId;

  private Long patientId;

  private LocalDateTime appointmentTime;

  private String reason;

}
