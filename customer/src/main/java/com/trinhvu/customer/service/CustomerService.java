package com.trinhvu.customer.service;

import com.trinhvu.customer.config.KeycloakPropsConfig;
import com.trinhvu.customer.exception.Forbidden;
import com.trinhvu.customer.viewmodel.CustomerAdminVm;
import com.trinhvu.customer.viewmodel.CustomerListVm;
import com.trinhvu.customer.viewmodel.CustomerPutVm;
import com.trinhvu.customer.viewmodel.CustomerVm;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.stereotype.Service;

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
            System.out.println(isConnected());
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
        return null;
    }

    public CustomerVm getCustomerProfile() {
        return null;
    }

    public CustomerVm createGuestUser() {
        return null;
    }

    public Void updateProfile(CustomerPutVm customerVm) {
        return null;
    }

    private boolean isConnected() {
        try {
            // Kiểm tra bằng cách lấy thông tin realm
            RealmRepresentation realmRepresentation = keycloak.realm(keycloakPropsConfig.getRealm()).toRepresentation();
            System.out.println("Connected to Keycloak: " + realmRepresentation.getRealm());
            return true;
        } catch (Exception e) {
            System.err.println("Failed to connect to Keycloak: " + e.getMessage());
            return false;
        }
    }
}
