package com.ria.loanflow.service.impl;

import com.ria.loanflow.dtos.req.CustomerRequest;
import com.ria.loanflow.dtos.res.CustomerResponse;
import com.ria.loanflow.entity.Customer;
import com.ria.loanflow.exception.DuplicateResourceException;
import com.ria.loanflow.mapper.CustomerMapper;
import com.ria.loanflow.repository.CustomerRepository;
import com.ria.loanflow.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    /*
    Autowired - null can be saved - so lets do costructor depenedency
    */
//    @Autowired
//    private CustomerRepository customerRepository;

//    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;

    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating customer with PAN: {} and Mobile: {}", request.pan(), request.mobile());

        if(customerRepository.existsByPan(request.pan())){
            throw new DuplicateResourceException("Customer already exists with PAN: " + request.pan());
        }
        if(customerRepository.existsByMobile(request.mobile())) {
            throw new DuplicateResourceException("Customer already exists with Mobile: " + request.mobile());
        }
        if (request.email() != null
                && !request.email().isBlank()
                && customerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Customer already exists with Email: " + request.email());
        }
        Customer customer = CustomerMapper.toEntity(request);
        Customer savedCustomer = customerRepository.save(customer);
        savedCustomer.setCustomerId(
                generateCustomerId(savedCustomer.getId())
        );
        savedCustomer = customerRepository.save(savedCustomer);
        log.info(
                "Customer created successfully. Customer ID: {}",
                savedCustomer.getCustomerId()
        );
        return CustomerMapper.toResponse(savedCustomer);

    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        return null;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return List.of();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }

    private String generateCustomerId(Long id) {
        return String.format("CUST%06d", id);
    }
}
