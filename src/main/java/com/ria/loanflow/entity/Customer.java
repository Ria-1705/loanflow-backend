package com.ria.loanflow.entity;

import com.ria.loanflow.common.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "customer_id"),
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "pan"),
                @UniqueConstraint(columnNames = "aadhaar")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", unique = true, length = 20)
    private String customerId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    private String pan;

    @Column(nullable = false, unique = true, length = 12)
    private String aadhaar;

    @Column(nullable = false)
    private LocalDate dob;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
