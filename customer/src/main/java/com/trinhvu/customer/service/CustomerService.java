package com.trinhvu.customer.service;

import com.trinhvu.customer.config.KeycloakPropsConfig;
import com.trinhvu.customer.exception.BadRequestException;
import com.trinhvu.customer.exception.Forbidden;
import com.trinhvu.customer.exception.NotFoundException;
import com.trinhvu.customer.utils.Constants;
import com.trinhvu.customer.viewmodel.*;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.utils.EmailValidationUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private static final String ERROR_FORMAT = "%s: Client %s don't have access right for this resource";
    private static final int USER_PER_PAGE = 2;
    private static final String GUEST = "GUEST";
    private final Keycloak keycloak;
    private final KeycloakPropsConfig keycloakPropsConfig;

    public static CredentialRepresentation createPasswordCredential(String password){
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);
        cred.setTemporary(false);
        return cred;
    }

    public CustomerListVm getCustomers(int pageNo) {
        try {
            List<CustomerAdminVm> result = keycloak.realm(keycloakPropsConfig.getRealm()).users()
                    .search(null, pageNo * USER_PER_PAGE, USER_PER_PAGE).stream()
                    .map(CustomerAdminVm::fromModel)
                    .toList();
            int totalUser = keycloak.realm(keycloakPropsConfig.getRealm()).users().count();
            return new CustomerListVm(totalUser, result, totalUser / USER_PER_PAGE);

        }catch (Forbidden e){
            throw new Forbidden(ERROR_FORMAT, e.getMessage(), keycloakPropsConfig.getResource());
        }
    }

    public CustomerAdminVm getCustomerByEmail(String email) {
        try {
            if (EmailValidationUtil.isValidEmail(email)) {
                List<UserRepresentation> result = keycloak.realm(keycloakPropsConfig.getRealm()).users()
                        .searchByEmail(email, true);
                if (result.isEmpty()) {
                    throw new NotFoundException(Constants.ErrorCode.USER_WITH_EMAIL_NOT_FOUND, email);
                }
                return CustomerAdminVm.fromModel(result.get(0));
            }else {
                throw new BadRequestException(Constants.ErrorCode.WRONG_EMAIL_FORMAT, email);
            }
        }catch (Forbidden e){
            throw new Forbidden(ERROR_FORMAT, e.getMessage(), keycloakPropsConfig.getResource());
        }
    }

    public CustomerVm getCustomerProfile(String userId) {
        try {
            UserRepresentation result = keycloak.realm(keycloakPropsConfig.getRealm()).users()
                    .get(userId).toRepresentation();

            return CustomerVm.fromModel(result);
        }catch (Forbidden e){
            throw new Forbidden(ERROR_FORMAT, e.getMessage(), keycloakPropsConfig.getResource());
        }
    }

    public GuestVm createGuestUser() {
        RealmResource realmResource = keycloak.realm(keycloakPropsConfig.getRealm());
        String randomName = generateSafeString();
        String guestEmail = randomName + "_guest@guest.com";
        CredentialRepresentation cred = createPasswordCredential(GUEST);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(guestEmail);
        user.setEmail(guestEmail);
        user.setFirstName(GUEST);
        user.setLastName(randomName);
        user.setCredentials(Collections.singletonList(cred));
        user.setEnabled(true);
        Response response = realmResource.users().create(user);

        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = realmResource.users().get(userId);
        RoleRepresentation roleRepresentation = realmResource.roles().get(GUEST).toRepresentation();

        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));

        return new GuestVm(userId, guestEmail, GUEST);
    }

    public void updateProfile(CustomerPutVm customerVm) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRepresentation user =
                keycloak.realm(keycloakPropsConfig.getRealm()).users()
                        .get(id).toRepresentation();
        if (user != null){
            user.setFirstName(customerVm.firstName());
            user.setLastName(customerVm.lastName());
            user.setEmail(customerVm.email());
            RealmResource resource = keycloak.realm(keycloakPropsConfig.getRealm());
            UserResource userResource = resource.users().get(user.getId());
            userResource.update(user);
        }else {
            throw new NotFoundException(Constants.ErrorCode.USER_NOT_FOUND);
        }
    }

    private boolean isConnected() {
        try {
            RealmRepresentation realmRepresentation = keycloak.realm(keycloakPropsConfig.getRealm()).toRepresentation();
            System.out.println("Connected to Keycloak: " + realmRepresentation.getRealm());
            return true;
        } catch (Exception e) {
            System.err.println("Failed to connect to Keycloak: " + e.getMessage());
            return false;
        }
    }

    private String generateSafeString(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[12];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
