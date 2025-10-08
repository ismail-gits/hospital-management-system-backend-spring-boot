package com.project.hospitalManagementSystem.entity;

import com.project.hospitalManagementSystem.entity.type.AppointmentStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime appointmentTime;

  @Column(nullable = false, length = 100)
  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatusType status;

  @ManyToOne
  @JoinColumn(name = "doctor_id", nullable = false) // owning side for doctor
  private Doctor doctor;

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false) // owning side for patient
  private Patient patient;
}
