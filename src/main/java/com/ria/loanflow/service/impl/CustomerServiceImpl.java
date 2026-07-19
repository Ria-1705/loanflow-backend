package com.ria.loanflow.service.impl;

import com.ria.loanflow.common.enums.CustomerStatus;
import com.ria.loanflow.dtos.req.CustomerRequest;
import com.ria.loanflow.dtos.req.UpdateCustomerRequest;
import com.ria.loanflow.dtos.res.CustomerResponse;
import com.ria.loanflow.entity.Customer;
import com.ria.loanflow.exception.DuplicateResourceException;
import com.ria.loanflow.exception.ResourceNotFoundException;
import com.ria.loanflow.mapper.CustomerMapper;
import com.ria.loanflow.repository.CustomerRepository;
import com.ria.loanflow.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CustomerResponse getCustomerById(String customerId) {

        Customer customer =  customerRepository.findByCustomerIdAndStatus(customerId, CustomerStatus.ACTIVE)
                .orElseThrow(()-> new ResourceNotFoundException( "Customer not found with ID: " + customerId));
        return CustomerMapper.toResponse(customer);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers() {
        return null;
    }

    @Override
    public CustomerResponse updateCustomer(String customerId, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findByCustomerIdAndStatus(customerId, CustomerStatus.ACTIVE)
                .orElseThrow(()-> new ResourceNotFoundException(  "Customer not found with ID: " + customerId));

        // Check duplicate mobile
        customerRepository.findByMobile(request.mobile())
                .ifPresent(existingCustomer -> {
                    if (!existingCustomer.getCustomerId()
                            .equals(customerId)) {

                        throw new DuplicateResourceException(
                                "Customer already exists with mobile: "
                                        + request.mobile());
                    }
                });

        // Check duplicate email
        if (request.email() != null && !request.email().isBlank()) {

            customerRepository.findByEmail(request.email())
                    .ifPresent(existingCustomer -> {
                        if (!existingCustomer.getCustomerId()
                                .equals(customerId)) {

                            throw new DuplicateResourceException(
                                    "Customer already exists with email: "
                                            + request.email());
                        }
                    });
        }
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setMobile(request.mobile());
        customer.setEmail(request.email());
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }



    @Override
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with ID: " + customerId));

        customer.setStatus(CustomerStatus.INACTIVE);

        customerRepository.save(customer);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findByStatus(CustomerStatus.ACTIVE, pageable);
        return customers.map(CustomerMapper::toResponse);
    }

    private String generateCustomerId(Long id) {
        return String.format("CUST%06d", id);
    }
}
