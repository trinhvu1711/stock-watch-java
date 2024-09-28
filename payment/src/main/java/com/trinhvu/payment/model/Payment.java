package com.trinhvu.payment.model;

import com.trinhvu.payment.model.enumeration.PaymentMethod;
import com.trinhvu.payment.model.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String failureMessage;
    @CreationTimestamp
    private ZonedDateTime createdOn;
    @UpdateTimestamp
    private ZonedDateTime lastModifiedOn;
}
