package com.ria.loanflow.mapper;

import com.ria.loanflow.dtos.req.CustomerRequest;
import com.ria.loanflow.dtos.res.CustomerResponse;
import com.ria.loanflow.entity.Customer;

public class CustomerMapper {

    private CustomerMapper(){}

    public static Customer toEntity(CustomerRequest request){
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .mobile(request.mobile())
                .email(request.email())
                .pan(request.pan())
                .aadhaar(request.aadhaar())
                .dob(request.dob())
                .build();
    }

    public  static CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getPan(),
                customer.getDob(),
                customer.getStatus()
        );

    }
}
