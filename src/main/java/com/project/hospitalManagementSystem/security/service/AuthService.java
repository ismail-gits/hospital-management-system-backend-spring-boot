package com.project.hospitalManagementSystem.security.service;

import com.project.hospitalManagementSystem.dto.LoginRequestDto;
import com.project.hospitalManagementSystem.dto.LoginResponseDto;
import com.project.hospitalManagementSystem.dto.SignupRequestDto;
import com.project.hospitalManagementSystem.dto.SignupResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
  LoginResponseDto login(LoginRequestDto loginRequestDto);

  SignupResponseDto signup(SignupRequestDto signupRequestDto);

  ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId);
}
