package kh.edu.cstad.bankingapi.security;

import jakarta.persistence.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtConverterConfig implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final Logger log = LoggerFactory.getLogger(JwtConverterConfig.class);
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> roles = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt,
                roles,
                null
        );
    }
    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt){
        Map<String, Object> resourceAccess;
        Map<String, Object> clientAccess;
        List<String> roles;
        resourceAccess = jwt.getClaim("resource_access");
        if(resourceAccess==null){
            return Set.of();
        }
        clientAccess = (Map<String, Object>) resourceAccess.get("nextjs");
        if(clientAccess==null){
            return Set.of();
        }
        roles = (List<String>) clientAccess.get("roles");//
        log.info("ROLES: " + roles);
        return roles.stream()
                .map(r->new SimpleGrantedAuthority("ROLE_" + r))
                .toList();
    }
}
