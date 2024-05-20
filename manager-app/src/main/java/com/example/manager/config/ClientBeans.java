package com.example.manager.config;

import com.example.manager.client.RestClientProductsRestClient;
import com.example.manager.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

  @Bean
  public RestClientProductsRestClient productsRestClient(
      @Value("${services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository,
      @Value("${services.catalogue.registration-id:keycloak}") String registrationId) {
    return new RestClientProductsRestClient(RestClient.builder()
        .baseUrl(catalogueBaseUri)
        .requestInterceptor(new OAuthClientHttpRequestInterceptor(
            new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, oAuth2AuthorizedClientRepository), registrationId))
        .build());
  }

}
