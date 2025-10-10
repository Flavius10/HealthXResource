package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${keySetURI}")
    private String jwtsKey;

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri(jwtsKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChainResourceServer(HttpSecurity http) throws Exception {

        http
            .csrf(c -> c.disable())

            .authorizeHttpRequests(
                    authorize ->
                        authorize.requestMatchers(HttpMethod.DELETE, "/profile").hasAuthority("admin")
                                 .requestMatchers(HttpMethod.DELETE, "/metric").hasAuthority("admin")
                                 .requestMatchers(HttpMethod.POST, "/advice").hasAuthority("advice")
                                 .anyRequest().authenticated()
            )
            .oauth2ResourceServer(
                  c -> c.jwt(
                          j -> j.jwtAuthenticationConverter(jwtAuthenticationConverter())
                                  .decoder(jwtDecoder())
                  )
            );


      return http.build();

    }

}
