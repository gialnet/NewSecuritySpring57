package com.vivaldispring.newsecurity.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

public class CustomAuthenticationManager implements AuthenticationManager {


    private final ShouldAuthenticateAgainstThirdPartySystem shouldAuth;

    public CustomAuthenticationManager(ShouldAuthenticateAgainstThirdPartySystem shouldAuth) {
        this.shouldAuth = shouldAuth;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (shouldAuth.AuthenticateAgainstThirdPartySystem(name, password)) {

            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<>());
        } else {
            return null;
        }
    }
}
