package com.project.hospitalManagementSystem.dto;

import com.project.hospitalManagementSystem.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

  private String name;

  private String username;

  private String password;

  private Set<RoleType> roles = new HashSet<>();
}
