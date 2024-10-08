package com.trinhvu.cart.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "carts")
public class Cart extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private Long orderId;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<CartItem> cartItems= new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
