package com.gaf.anagram.config;


import com.gaf.anagram.models.Login;
import com.gaf.anagram.services.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    Authenticate authenticate;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();
        // do authentication action here
        Login loging = new Login();
        loging.setUsername(username);
        loging.setPassword(password);
        if (authenticate.doAuthenticate(loging)) {
            return new CustomUserObj(
                    username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }

    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
