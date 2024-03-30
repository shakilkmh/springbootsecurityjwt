package com.mollah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mollah.dto.AuthenticationDTO;
import com.mollah.record.AuthenticationResponse;
import com.mollah.service.jwt.UserDetailsServiceImpl;
import com.mollah.util.JwtUtil;

import io.micrometer.common.util.StringUtils;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO) throws BadCredentialsException, UsernameNotFoundException {
        
    	if(StringUtils.isEmpty(authenticationDTO.getEmail()) || StringUtils.isEmpty(authenticationDTO.getPassword())) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and Password are required");
    	}
   	
    	try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse(jwt));

    }

}
