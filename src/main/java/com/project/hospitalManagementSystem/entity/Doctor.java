package com.project.hospitalManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @MapsId
  private User user;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false, unique = true, length = 30)
  private String email;

  @Column(length = 50)
  private String specialization;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "doctor") // inverse side for appointment
  private List<Appointment> apppointmentList = new ArrayList<>();

  @OneToOne(mappedBy = "headDoctor") // inverse side for department for headDoctor
  private Department department;

  @ManyToMany(mappedBy = "doctorSet") // inverse side for department for doctorSet
  private Set<Department> departmentSet = new HashSet<>();
}
