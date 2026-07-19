package com.ria.loanflow.dtos.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

    public record UpdateCustomerRequest(
            @NotBlank(message = "First name is required")
            String firstName,

            String lastName,

            @NotBlank(message = "Mobile number is required")
            @Pattern(regexp = "^[6-9]\\d{9}$",
                    message = "Invalid mobile number")
            String mobile,

            @Email(message = "Invalid email")
            String email

    ) {
    }
