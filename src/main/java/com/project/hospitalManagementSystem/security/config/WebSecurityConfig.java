package com.project.hospitalManagementSystem.security.config;

import com.project.hospitalManagementSystem.entity.type.PermissionType;
import com.project.hospitalManagementSystem.entity.type.RoleType;
import com.project.hospitalManagementSystem.security.filter.JwtAuthFilter;
import com.project.hospitalManagementSystem.security.util.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.project.hospitalManagementSystem.entity.type.PermissionType.*;
import static com.project.hospitalManagementSystem.entity.type.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final JwtAuthFilter jwtAuthFilter;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrfConfig -> csrfConfig.disable())
        .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**", "/auth/**").permitAll()
            .requestMatchers("/admin/**").hasRole(ADMIN.name())
            .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, "/admin/**").hasAnyAuthority(
                APPOINTMENT_DELETE.name(),
                USER_MANAGE.name()
            )
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .oauth2Login(oAuth2Config -> oAuth2Config
            .failureHandler((request, response, authenticationException) -> {
              log.error("OAuth2 error: {}", authenticationException.getMessage());
            })
            .successHandler(oAuth2SuccessHandler)
        )
        .exceptionHandling(exceptionConfig -> exceptionConfig
            .accessDeniedHandler(((request, response, accessDeniedException) -> {
              handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
            }))
        );
//        .formLogin(Customizer.withDefaults());

    return httpSecurity.build();
  }

  // In-Memory User Details Manager
//  @Bean
//  UserDetailsService userDetailsService() {
//    UserDetails user1 = User
//        .withUsername("admin")
//        .password(passwordEncoder.encode("admin123"))
//        .roles("ADMIN")
//        .build();
//
//    UserDetails user2 = User
//        .withUsername("patient")
//        .password(passwordEncoder.encode("patient123"))
//        .roles("PATIENT")
//        .build();
//
//    return new InMemoryUserDetailsManager(user1, user2);
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
