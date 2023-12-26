package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.exception.BadApiRequest;
import com.bikkadit.electoronic.store.payload.JwtRequest;
import com.bikkadit.electoronic.store.payload.JwtResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.security.JwtTokenHelper;
import com.bikkadit.electoronic.store.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/current")
    public ResponseEntity<UserDetails> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(name), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);

        JwtResponse response = JwtResponse.builder()
                .JwtToken(token)
                .user(userDto).
                build();

        return new ResponseEntity<JwtResponse>(response, HttpStatus.CREATED);
    }

    private void doAuthenticate(String email, String password) {

        // We Authenticate from email and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new BadApiRequest("Invalid UserName & Password !!!");

        }


    }
}
