package com.example.CENProj.service;

import com.example.CENProj.model.Dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    /**
    * Returns UserDetails object that will be used by the SecurityContext to authenticate current User.
    * Security context will make UserDetails object returned in this function throughout app to access said user.
    *
    */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.CENProj.model.User user = this.userService.getUserByEmail(email);
        UserDto userDetails = new UserDto(user.getEmail(), user.getPassword(), new LinkedList<>());
        userDetails.setUser(user);
        return userDetails;
    }
}
