package com.project.hospitalManagementSystem.entity;

import com.project.hospitalManagementSystem.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = {
      @UniqueConstraint(name = "unique_patient_name_birth_date", columnNames = { "name", "birthDate" })
    },
    indexes = {
      @Index(name = "idx_patient_birth_date", columnList = "birthDate")
    }
)
public class Patient {

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

  private String gender;

  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  private BloodGroupType bloodGroup;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
  @JoinColumn(name = "insurance_id") // owning side for insurance
  private Insurance insurance;

  @OneToMany(mappedBy = "patient", cascade = { CascadeType.REMOVE }, orphanRemoval = true) // inverse side for appointment
  private List<Appointment> apppointmentList = new ArrayList<>();
}
