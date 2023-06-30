package com.example.springbootdemo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
    private final HashMap<String, String> roles = new HashMap<>();

    public UserService() {
        this.roles.put("2bc08eba-d9be-4b45-89ae-ac68fc7bc481", "admin");
    }

    @Override
    public UserDetails loadUserByUsername(String jwtToken) throws UsernameNotFoundException {
        DecodedJWT token = JWT.decode(jwtToken);

        User.UserBuilder builder = User.builder();

        String username;
        if (token.getClaim("upn").isMissing()) {
            username = token.getClaim("oid").asString();
            System.out.println("oid");
            System.out.println(username);
        } else {
            username = token.getClaim("upn").asString();
            System.out.println("upn");
            System.out.println(username);
        }

        builder.username(username);
        builder.password(jwtToken);

        builder.roles(this.roles.get(username));
        return builder.build();
    }
}
