package com.trinhvu.customer.repository;

import com.trinhvu.customer.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllByUserId(String userId);

    UserAddress findOneByUserIdAndAddressId(String userId, Long id);

    Optional<UserAddress> findByUserIdAndIsActiveTrue(String userId);
}
