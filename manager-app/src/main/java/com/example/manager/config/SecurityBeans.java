package com.example.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .anyRequest()
            .hasRole("MANAGER"))
            .oauth2Login(Customizer.withDefaults())
            .oauth2Client(Customizer.withDefaults())
        .build();
  }

  @Bean
  public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
    return new CustomOidcUserService();
  }

}
