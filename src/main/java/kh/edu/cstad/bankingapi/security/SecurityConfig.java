package kh.edu.cstad.bankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtConverterConfig jwtConverterConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception{
        httpSecurity.cors(e->e.disable());
        httpSecurity
                .authorizeHttpRequests(re->
                                re.requestMatchers("/api/v1/customers")
                                        .hasRole("admin")
                                        .anyRequest()
                                        .permitAll());
        // using token to access the resource, but ignoring users' role
//        httpSecurity.oauth2ResourceServer(jwt->jwt.jwt(Customizer.withDefaults()));
        // enable or using role base access control
        httpSecurity.oauth2ResourceServer(jwt->jwt.jwt(
                conveter->conveter.jwtAuthenticationConverter(jwtConverterConfig)
        ));
        httpSecurity.sessionManagement(se->se.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.csrf(e->e.disable());
        httpSecurity.httpBasic(e->e.disable());
        return httpSecurity.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User.withUsername("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1);
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
