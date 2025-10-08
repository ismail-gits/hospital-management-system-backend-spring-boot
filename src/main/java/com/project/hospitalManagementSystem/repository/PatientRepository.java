package com.project.hospitalManagementSystem.repository;

import com.project.hospitalManagementSystem.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

  // Find all patients with pagination
  @Query("SELECT p FROM Patient p")
  Page<Patient> findAllPatients(Pageable pageable);

}