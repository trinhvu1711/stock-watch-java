package com.trinhvu.customer.viewmodel;

import org.keycloak.representations.idm.UserRepresentation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public record CustomerAdminVm(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        LocalDateTime createdTimestamp
) {
    public static CustomerAdminVm fromModel(UserRepresentation user) {
        LocalDateTime createdTimestamp =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(user.getCreatedTimestamp()),
                        TimeZone.getDefault().toZoneId());
        return new CustomerAdminVm(user.getId(), user.getUsername(), user.getEmail(),
                user.getFirstName(), user.getLastName(), createdTimestamp);
    }
}
