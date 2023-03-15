package com.BlogApp5.controller;

import com.BlogApp5.entities.Role;
import com.BlogApp5.entities.User;
import com.BlogApp5.payload.LoginDto;
import com.BlogApp5.payload.SignUpDto;
import com.BlogApp5.repositories.RoleRepository;
import com.BlogApp5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto)
    {
        Authentication authenticate = authenticationManager.authenticate(
                                 new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("user sing-in successfully..!", HttpStatus.OK);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto)
    {

            if (userRepo.existsByUsername(signUpDto.getUsername())) {
                return new ResponseEntity<>("user is already taken..!", HttpStatus.BAD_REQUEST);
            }

            if (userRepo.existsByEmail(signUpDto.getEmail())) {
                return new ResponseEntity<>("Email is already taken..!", HttpStatus.BAD_REQUEST);
            }
        User user=new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

//        Role role=roleRepo.findByname("ROlE_ADMIN").get();
//        System.out.println(role);
//        user.setRoles(Collections.singleton(role));
//        userRepo.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

}
