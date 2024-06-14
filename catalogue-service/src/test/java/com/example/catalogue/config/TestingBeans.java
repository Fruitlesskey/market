package com.example.catalogue.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
public class TestingBeans {

  @Bean
  public JwtDecoder jwtDecoder() {
    return mock(JwtDecoder.class);
  }

}
