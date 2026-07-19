package com.ria.loanflow.repository;

import com.ria.loanflow.common.enums.CustomerStatus;
import com.ria.loanflow.entity.Customer;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(String customerId);

    Optional<Customer> findByPan(String pan);

    Optional<Customer> findByMobile(String mobile);

    boolean existsByPan(String pan);

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCustomerIdAndStatus(
            String customerId,
            CustomerStatus status
    );

    Page<Customer> findByStatus(
            CustomerStatus status,
            Pageable pageable
    );
}
