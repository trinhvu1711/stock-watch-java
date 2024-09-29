package com.trinhvu.notification.model;

public record Customer(
        String id,
        String firstname,
        String lastname,
        String email
) {
}