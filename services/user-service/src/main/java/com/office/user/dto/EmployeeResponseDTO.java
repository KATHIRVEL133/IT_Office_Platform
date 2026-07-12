package com.office.user.dto;

import com.office.user.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Role role;
    private String status;
}