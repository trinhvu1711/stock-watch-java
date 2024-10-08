package com.trinhvu.customer.viewmodel;

import org.keycloak.representations.idm.UserRepresentation;

public record CustomerVm(String username, String email, String firstName, String lastName) {
    public static CustomerVm fromModel(UserRepresentation user){
        return new CustomerVm(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }
}

