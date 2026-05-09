package com.nithin.authService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GreetingsController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasRole('USER')")
    public String sayHelloUser() {
        return "Welcome USER";
    }

    @GetMapping("/helloAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String sayHelloAdmin() {
        return "Welcome ADMIN";
    }
}