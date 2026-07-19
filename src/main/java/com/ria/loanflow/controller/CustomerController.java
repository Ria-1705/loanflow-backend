package com.ria.loanflow.controller;

import com.ria.loanflow.dtos.req.CustomerRequest;
import com.ria.loanflow.dtos.req.UpdateCustomerRequest;
import com.ria.loanflow.dtos.res.CustomerResponse;
import com.ria.loanflow.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return customerService.createCustomer(request);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String customerId){
        return ResponseEntity.ok(
                customerService.getCustomerById(customerId)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        log.info("Getting Customers in page format");


        return ResponseEntity.ok(
                customerService.getAllCustomers(pageable)
        );
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String customerId,
            @Valid @RequestBody UpdateCustomerRequest request){
    return ResponseEntity.ok(
            customerService.updateCustomer(customerId,request)
    );
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable String customerId) {

        customerService.deleteCustomer(customerId);

        return ResponseEntity.noContent().build();
    }
}
