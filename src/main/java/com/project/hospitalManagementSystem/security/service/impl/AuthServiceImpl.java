package com.project.hospitalManagementSystem.security.service.impl;

import com.project.hospitalManagementSystem.dto.LoginRequestDto;
import com.project.hospitalManagementSystem.dto.LoginResponseDto;
import com.project.hospitalManagementSystem.dto.SignupRequestDto;
import com.project.hospitalManagementSystem.dto.SignupResponseDto;
import com.project.hospitalManagementSystem.entity.Patient;
import com.project.hospitalManagementSystem.entity.User;
import com.project.hospitalManagementSystem.entity.type.AuthProviderType;
import com.project.hospitalManagementSystem.entity.type.RoleType;
import com.project.hospitalManagementSystem.repository.PatientRepository;
import com.project.hospitalManagementSystem.repository.UserRepository;
import com.project.hospitalManagementSystem.security.util.AuthUtil;
import com.project.hospitalManagementSystem.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final AuthUtil authUtil;

  private final UserRepository userRepository;
  private final PatientRepository patientRepository;

  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
    );

    User user = (User) authentication.getPrincipal();

    String token = authUtil.generateAccessToken(user);

    return new LoginResponseDto(token, user.getId());
  }

  @Override
  public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
    User user = signupInternal(signupRequestDto, AuthProviderType.EMAIL, null);
    return modelMapper.map(user, SignupResponseDto.class);
  }

  public User signupInternal(SignupRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId) {
    // Validate password requirement for EMAIL auth
    if (authProviderType == AuthProviderType.EMAIL && (signupRequestDto.getPassword() == null || signupRequestDto.getPassword().isBlank())) {
      throw new IllegalArgumentException("Password is required for EMAIL signup");
    }

    User user = userRepository
        .findByUsername(signupRequestDto.getUsername())
        .orElse(null);

    if (user != null) {
      throw new IllegalArgumentException("User already exists with username: " + signupRequestDto.getUsername());
    }

    user = User.builder()
        .username(signupRequestDto.getUsername())
        .providerId(providerId)
        .providerType(authProviderType)
        .roles(signupRequestDto.getRoles()) // Role.PATIENT is recommended not getRoles()
        .build();

    if (authProviderType == AuthProviderType.EMAIL) {
      user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
    }

    user = userRepository.save(user);

    Patient patient = Patient.builder()
        .name(signupRequestDto.getName())
        .email(signupRequestDto.getUsername())
        .user(user)
        .build();

    patientRepository.save(patient);

    return user;
  }

  @Override
  @Transactional
  public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
    AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
    String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

    User user = userRepository
        .findByProviderIdAndProviderType(providerId, providerType)
        .orElse(null);

    String email = oAuth2User.getAttribute("email");
    String name = oAuth2User.getAttribute("name");

    User emailUser = userRepository
        .findByUsername(email)
        .orElse(null);

    if (user == null && emailUser == null) {
      // Signup flow
      String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
      user = signupInternal(new SignupRequestDto(name, username, null, Set.of(RoleType.PATIENT)), providerType, providerId);
    } else if (user != null) {
      if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
        user.setUsername(email);
        userRepository.save(user);
      }
    } else {
      throw new BadCredentialsException("This email is already registered with provider: " + emailUser.getProviderType());
    }

    LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

    return ResponseEntity.ok(loginResponseDto);
  }
}
