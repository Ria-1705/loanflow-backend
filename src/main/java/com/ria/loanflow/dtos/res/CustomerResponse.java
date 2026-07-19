package com.ria.loanflow.dtos.res;

import com.ria.loanflow.common.enums.CustomerStatus;

import java.time.LocalDate;

public record CustomerResponse(

        String customerId,

        String firstName,

        String lastName,

        String mobile,

        String email,

        String pan,

        LocalDate dob,

        CustomerStatus status
) { }
