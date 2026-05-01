package com.library.springapilibrary.controller;

import com.library.springapilibrary.dto.AuthRequest;
import com.library.springapilibrary.dto.AuthResponse;
import com.library.springapilibrary.service.JwtService;
import com.library.springapilibrary.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * The @CrossOrigin annotation is no longer needed here, as a global CORS
 * configuration is now defined in SecurityConfig.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        // Authenticate the user using Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        // If authentication is successful, generate a JWT
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            // This case should ideally not be reached if authenticationManager.authenticate throws an exception on failure
            return ResponseEntity.status(401).body(new AuthResponse("Authentication failed"));
        }
    }
}
