package com.mollah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mollah.dto.SignupDTO;
import com.mollah.dto.UserDTO;
import com.mollah.service.auth.AuthService;

@RestController
public class SignupController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
       Object response = authService.createUser(signupDTO);
       if (response instanceof UserDTO){
    	   return new ResponseEntity<>(response, HttpStatus.CREATED);
       }
       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
