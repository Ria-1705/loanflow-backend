package com.ria.loanflow.service;

import com.ria.loanflow.dtos.req.CustomerRequest;
import com.ria.loanflow.dtos.req.UpdateCustomerRequest;
import com.ria.loanflow.dtos.res.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(String customerId);

//    List<CustomerResponse> getAllCustomers();

    Page<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(String customerId, UpdateCustomerRequest request);

//    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(String customerId);

    Page<CustomerResponse> getAllCustomers(Pageable pageable);
}
