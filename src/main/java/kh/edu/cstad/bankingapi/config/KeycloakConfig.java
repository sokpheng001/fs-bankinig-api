package kh.edu.cstad.bankingapi.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class KeycloakConfig {
    @Value("${keycloak.url}")
    private String keycloakUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.realm}")
    private String realm;
    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .clientId(clientId)
                .realm(realm)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
