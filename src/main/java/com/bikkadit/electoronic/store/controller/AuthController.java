package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.exception.BadApiRequest;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.JwtRequest;
import com.bikkadit.electoronic.store.payload.JwtResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.security.JwtTokenHelper;
import com.bikkadit.electoronic.store.service.UserServiceI;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
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

    @Value("${googleClientId}")
    private String googleClientId;
    @Value("${newPassword}")
    private String newPassword;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

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
    // Login With Google

    @PostMapping("/google")
    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Objects> data) throws IOException {

//        //get Id Token From Request
//        String idToken = data.get("idToken").toString();
//
//        // To Create Verifier
//        NetHttpTransport netHttpTransport = new NetHttpTransport();
//
//        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//        // We Need to provide id here
//
//        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.
//                Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));
//
//        // We Create Google Id
//        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);
//        GoogleIdToken.Payload payload = googleIdToken.getPayload();
//
//        logger.info("Payload :{}", payload.getEmail());
//        String email = payload.getEmail();
//
//        User user = null;
//        user = userServiceI.findUserByEmailOptional(email).orElse(null);
//
//        if (user == null) {
//            user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString());
//        }
//        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.loginUser(JwtRequest.builder()
//                .email(user.getEmail()).password(newPassword).build());
//
//        return jwtResponseResponseEntity;

//        public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Object> data) throws IOException {

            //get idToken from request
            String idToken = data.get("idToken").toString();
            NetHttpTransport netHttpTransport = new NetHttpTransport();
            JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));
            GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            logger.info("Payload :{}", payload);
            String email = payload.getEmail();

            User user = null;
            user = userServiceI.findUserByEmailOptional(email).orElse(null);

            if (user == null) {
                //create new user
                user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString());
            }

            ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.loginUser(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());

            return jwtResponseResponseEntity;
        }





    public User saveUser(String email, String name, String photoUrl) {

        UserDto newUser = UserDto.builder()
                .name(name)
                .email(email)
                .password(newPassword)
                .imageName(photoUrl)
                .roles(new HashSet<>())
                .build();

        UserDto user = userServiceI.createUser(newUser);

        return this.modelMapper.map(user, User.class);
    }

}
