package com.trinhvu.order.model;

import com.trinhvu.order.model.enumeration.CheckoutState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "checkout")
public class Checkout {
    @Id
    private String id;
    private String email;
    private String note;
    @Enumerated(EnumType.STRING)
    private CheckoutState  checkoutState;
    @OneToMany(mappedBy = "checkoutId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CheckoutItem> checkoutItemSet;
}
