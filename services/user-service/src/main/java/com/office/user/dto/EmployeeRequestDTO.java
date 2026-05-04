package com.office.user.dto;

import com.office.user.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class EmployeeRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String department;

    private Role role;

    private String status;
}