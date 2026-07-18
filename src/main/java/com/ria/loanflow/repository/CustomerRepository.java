package com.ria.loanflow.repository;

import com.ria.loanflow.entity.Customer;
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
}
