package com.BlogApp5.security;

import com.BlogApp5.entities.Role;
import com.BlogApp5.entities.User;
import com.BlogApp5.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsServices implements UserDetailsService
{
    private UserRepository userRepo;

    public CustomUserDetailsServices(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found with username :"+username)
        );
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles)
    {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
