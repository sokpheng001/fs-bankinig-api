package kh.edu.cstad.bankingapi.service.impl;

import jakarta.ws.rs.core.Response;
import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.RegisterCustomerReq;
import kh.edu.cstad.bankingapi.dto.ResetPasswordRequest;
import kh.edu.cstad.bankingapi.exception.ConfirmPasswordException;
import kh.edu.cstad.bankingapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    @Override
    public CustomerResponse register(RegisterCustomerReq register) {
        if(!register.password().equals(register.confirmPassword())){
            throw new ConfirmPasswordException("Password and Confirm Password must be the same 🤦‍♂️");
        }
        // create object of user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(register.fullName());
        user.setEmail(register.email());
        user.setFirstName("a");
        user.setLastName("b");
        // set more attribute
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber",List.of("123456778"));
        attributes.put("gender",List.of("Male"));
        user.setAttributes(attributes);
        //
        user.setEnabled(true);
        user.setEmailVerified(false);
        // set password of user
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(OAuth2Constants.PASSWORD);
        credential.setValue(register.password());
        // set password to user
        user.setCredentials(List.of(credential));
        // create user
        try(Response response = keycloak.realm(realm).users().create(user)){
            log.info("Status: {}", response.getStatus());
            if(response.getStatus()== HttpStatus.SC_CREATED){
                String getUserIdFromKeycloak = getCreatedId(response);
                boolean isSetRole = setRoleForUser(getUserIdFromKeycloak, realm);
                if(isSetRole){
                    log.info("Role set to user successfully");
                }else {
                    log.error("Cannot set role to user");
                }
                if(getUserIdFromKeycloak!=null){
                    // handle it
                    //
                    boolean isSentMail = isSentEmailVerified(getUserIdFromKeycloak, realm);
                    if (!isSentMail){
                        // handle exception for email verifying
                    }else {
                        log.info("Email sent successfully");
                    }

                }
                return CustomerResponse.builder()
                        .fullName(user.getFirstName())
                        .email(user.getEmail())
                        .uuid(user.getId())
                        .build();
            }else {
                // handle exception
                log.warn("Problem while creating new customer in auth Service");
            }
        }catch (Exception exception){
            log.error("Error during creating new customer: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public CustomerResponse resetPassword(Authentication authentication, ResetPasswordRequest resetPasswordRequest) {
        if(!resetPasswordRequest.newPassword().equals(resetPasswordRequest.newConfirmPassword())){
            throw new ConfirmPasswordException("New password and Confirm password must be the same");
        }
        Jwt jwt =(Jwt) authentication.getPrincipal();
        String userId  = jwt.getSubject();
        UserRepresentation user = keycloak.realm(realm)
                .users().get(userId).toRepresentation();
        if(user!=null){
            if(user.getEmail().equals(resetPasswordRequest.email())){
                CredentialRepresentation newCredential = new CredentialRepresentation();
                newCredential.setValue(resetPasswordRequest.newPassword());
                keycloak.realm(realm).users()
                        .get(userId)
                        .resetPassword(newCredential);
                log.info("Successfully reset Password");
                return CustomerResponse.builder()
                        .uuid(userId)
                        .email(user.getEmail())
                        .build();
            }
        }
        return null;
    }

    @Override
    public CustomerResponse forgotPassword(String email) {
        UserRepresentation user = keycloak
                .realm(realm).users().searchByEmail(email, true).getFirst();
        if(user==null){
            throw new UsernameNotFoundException("User is not found");
        }
        keycloak.realm(realm)
                .users().get(user.getId())
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
        return CustomerResponse.builder()
                .fullName(user.getFirstName())
                .email(user.getEmail())
                .build();
    }

    public boolean isSentEmailVerified(String userId, String realmName){
        try {
            RealmResource realmResource =keycloak.realm(realmName);
            UserResource userResource = realmResource.users().get(userId);
            //
            UserRepresentation user = userResource.toRepresentation();
            List<String>actions  = List.of("VERIFY_EMAIL");
            user.setRequiredActions(actions);
            userResource.update(user);
            keycloak.realm(realmName).users().get(userId).executeActionsEmail(actions);
            return true;
        }catch (Exception ignore){}
        return false;
    }
    private boolean setRoleForUser(String userId, String realmName){
        try{
            UserResource userResource = keycloak.realm("nextjs").users().get(userId);

            String clientUuid = keycloak.realm(realmName)
                    .clients()
                    .findByClientId(clientId)
                    .getFirst()
                    .getId();

            RoleRepresentation role = keycloak.realm(realmName)
                    .clients()
                    .get(clientUuid)
                    .roles()
                    .get("user")
                    .toRepresentation();

            userResource.roles()
                    .clientLevel(clientUuid)
                    .add(Collections.singletonList(role));
            return true;
        }catch (Exception ignore){}
        return false;
    }
    private String getCreatedId(Response response) {
        String location = response.getHeaderString("Location");
        if (location != null) {
            return location.substring(location.lastIndexOf("/") + 1);
        }
        return null;
    }
}
