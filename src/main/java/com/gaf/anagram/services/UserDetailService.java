package com.gaf.anagram.services;


import com.gaf.anagram.models.CustomUserDetails;
import com.gaf.anagram.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = new User();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
}
