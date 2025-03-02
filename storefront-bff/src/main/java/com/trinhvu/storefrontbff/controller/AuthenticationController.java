package com.trinhvu.storefrontbff.controller;

import com.trinhvu.storefrontbff.viewmodel.AuthenticatedUser;
import com.trinhvu.storefrontbff.viewmodel.AuthenticationInfoVm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @GetMapping("/authentication")
    public ResponseEntity<AuthenticationInfoVm> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(new AuthenticationInfoVm(false, null));
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(principal.getAttribute("preferred_username"));
        return ResponseEntity.ok(new AuthenticationInfoVm(true, authenticatedUser));
    }
}