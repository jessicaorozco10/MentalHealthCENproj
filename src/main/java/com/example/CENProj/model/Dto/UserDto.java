package com.example.CENProj.model.Dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/* Extends Spring securit user class to include local project user
* This user will be available through the program when we get the security context
* Helps track logged-in user
* */
@Setter
@Getter
public class UserDto extends User {
    private com.example.CENProj.model.User user;

    public UserDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserDto(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public void setUser(com.example.CENProj.model.User user) {
        this.user = user;
    }

    public com.example.CENProj.model.User getUser() {
        return user;
    }
}
