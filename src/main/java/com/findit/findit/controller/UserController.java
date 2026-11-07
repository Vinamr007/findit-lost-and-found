
package com.findit.findit.controller;

import com.findit.findit.dto.UserLoginRequest;
import com.findit.findit.dto.UserRegistrationRequest;
import com.findit.findit.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody UserRegistrationRequest request) {

        userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest request) {

        boolean isLoggedIn = userService.loginUser(request);

        return ResponseEntity.ok("Login successful!");
    }
}
