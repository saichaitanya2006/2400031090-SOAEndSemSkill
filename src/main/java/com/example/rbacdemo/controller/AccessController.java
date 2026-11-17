package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.UserRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccessController {

    // Common endpoint for all roles
    @GetMapping("/portal")
    public String portal(Authentication auth) {

        String role = auth.getAuthorities()
                .iterator().next().getAuthority();

        return "Welcome " + auth.getName()
                + " Your Role: " + role;
    }

    // ADMIN only
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(
                "User created successfully: " + request.getName());
    }

    // ADMIN and ADMINISTRATOR
    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN','ADMINISTRATOR')")
    public String reports() {

        return "Enterprise reports accessed successfully";
    }

    // USER only
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String profile() {

        return "User profile accessed successfully";
    }

    // ADMIN only
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable int id) {

        return "User " + id + " deleted successfully";
    }
}