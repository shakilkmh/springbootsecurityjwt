package com.mollah.controller;

import com.mollah.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mollah.record.HelloResponse;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello/{email}")
    public HelloResponse hello(@PathVariable String email) {
        if(email != null) {
            return new HelloResponse("Welcome ".concat(authService.getUserByEmail(email).getName()), new Date());
        }
        return new HelloResponse("Email not found.", new Date());
    }

}
