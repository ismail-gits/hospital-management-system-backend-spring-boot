package com.project.hospitalManagementSystem.entity;

import com.project.hospitalManagementSystem.entity.type.AuthProviderType;
import com.project.hospitalManagementSystem.entity.type.RoleType;
import com.project.hospitalManagementSystem.security.util.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "app_user",
    indexes = {
        @Index(name = "idx_provider_id_provider_type", columnList = "providerId, providerType")
    }
)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 100)
  private String username;

  private String password;

  private String providerId;

  @Enumerated(EnumType.STRING)
  private AuthProviderType providerType;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<RoleType> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return roles
//        .stream()
//        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//        .collect(Collectors.toSet());

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    roles.forEach(
        role -> {
          Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthoritiesForRole(role);
          authorities.addAll(permissions);
          authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
    );

    return authorities;
  }
}
