package com.project.hospitalManagementSystem.security.util;

import com.project.hospitalManagementSystem.entity.type.PermissionType;
import com.project.hospitalManagementSystem.entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.hospitalManagementSystem.entity.type.PermissionType.*;
import static com.project.hospitalManagementSystem.entity.type.RoleType.*;

@Component
public class RolePermissionMapping {

  private static final Map<RoleType, Set<PermissionType>> map = Map.of(
      PATIENT, Set.of(PATIENT_READ, APPOINTMENT_READ, APPOINTMENT_WRITE),
      DOCTOR, Set.of(APPOINTMENT_DELETE, APPOINTMENT_WRITE, APPOINTMENT_READ, PATIENT_READ),
      ADMIN, Set.of(PATIENT_READ, PATIENT_WRITE, APPOINTMENT_READ, APPOINTMENT_WRITE, APPOINTMENT_DELETE, USER_MANAGE, REPORT_VIEW)
  );

  public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {
    return map.get(role)
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
        .collect(Collectors.toSet());
  }

}
