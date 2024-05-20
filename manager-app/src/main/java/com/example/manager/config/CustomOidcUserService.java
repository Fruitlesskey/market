package com.example.manager.config;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

  private final OidcUserService delegate = new OidcUserService();

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) {
    OidcUser oidcUser = delegate.loadUser(userRequest);

    List<GrantedAuthority> authorities = Stream.concat(oidcUser.getAuthorities().stream(),
            Optional.ofNullable(
                    oidcUser.getClaimAsStringList("groups"))
                .orElseGet(List::of)
                .stream()
                .filter(role -> role.startsWith("ROLE_"))
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast))
        .toList();

    return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
  }

}
