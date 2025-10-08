package com.project.hospitalManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hospitalManagementSystem.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {

  private Long id;

  private String name;

  private String email;

  private String gender;

  @JsonProperty("birth_date")
  private LocalDate birthDate;

  @JsonProperty("blood_group")
  private BloodGroupType bloodGroup;
}
