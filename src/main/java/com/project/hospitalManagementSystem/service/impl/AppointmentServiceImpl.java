package com.project.hospitalManagementSystem.service.impl;

import com.project.hospitalManagementSystem.dto.AppointmentResponseDto;
import com.project.hospitalManagementSystem.dto.CreateAppointmentDto;
import com.project.hospitalManagementSystem.entity.Appointment;
import com.project.hospitalManagementSystem.entity.Doctor;
import com.project.hospitalManagementSystem.entity.Patient;
import com.project.hospitalManagementSystem.entity.type.AppointmentStatusType;
import com.project.hospitalManagementSystem.repository.AppointmentRepository;
import com.project.hospitalManagementSystem.repository.DoctorRepository;
import com.project.hospitalManagementSystem.repository.PatientRepository;
import com.project.hospitalManagementSystem.service.AppointmentService;
import com.project.hospitalManagementSystem.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

  private DoctorRepository doctorRepository;
  private PatientRepository patientRepository;
  private ModelMapper modelMapper;
  private final AppointmentRepository appointmentRepository;

  @Override
  @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') AND #id == authentication.principal.id)")
  public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
    Doctor doctor = doctorRepository
        .findById(doctorId)
        .orElseThrow();

    return doctor.getApppointmentList()
        .stream()
        .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  @Secured("ROLE_PATIENT")
  public AppointmentResponseDto createNewAppointment(CreateAppointmentDto createAppointmentDto) {
    Long doctorId = createAppointmentDto.getDoctorId();
    Long patientId = createAppointmentDto.getPatientId();

    Doctor doctor = doctorRepository
        .findById(doctorId)
        .orElseThrow(() -> new EntityNotFoundException("No doctor found with id: " + doctorId));

    Patient patient = patientRepository
        .findById(patientId)
        .orElseThrow(() -> new EntityNotFoundException("No patient found with id: " + patientId));

    Appointment appointment = Appointment.builder()
        .doctor(doctor)
        .patient(patient)
        .status(AppointmentStatusType.PENDING)
        .appointmentTime(createAppointmentDto.getAppointmentTime())
        .reason(createAppointmentDto.getReason())
        .build();

    patient.getApppointmentList().add(appointment); // To maintain consistency

    appointment = appointmentRepository.save(appointment);

    return modelMapper.map(appointment, AppointmentResponseDto.class);
  }

  @Override
  @Transactional
  @PreAuthorize("hasAuthority('appointment:write') OR #doctorId == authentication.principal.id")
  public AppointmentResponseDto reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
    Appointment appointment = appointmentRepository
        .findById(appointmentId)
        .orElseThrow(() -> new EntityNotFoundException("No appointment found with id: " + appointmentId));

    Doctor doctor = doctorRepository
        .findById(doctorId)
        .orElseThrow(() -> new EntityNotFoundException("No doctor found with id: " + doctorId));

    appointment.setDoctor(doctor); // this will automatically the save()

    doctor.getApppointmentList().add(appointment); // to maintain bidirectional consistency

    return modelMapper.map(appointment, AppointmentResponseDto.class);
  }
}
