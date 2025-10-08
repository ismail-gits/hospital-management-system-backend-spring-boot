package com.project.hospitalManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

  @JsonProperty("access_token")
  private String jwt;

  @JsonProperty("user_id")
  private Long userId;
}
