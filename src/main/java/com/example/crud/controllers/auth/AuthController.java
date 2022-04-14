package com.example.crud.controllers.auth;

import com.example.crud.models.ERole;
import com.example.crud.models.Role;
import com.example.crud.models.User;
import com.example.crud.payload.request.LoginRequest;
import com.example.crud.payload.request.RegisterRequest;
import com.example.crud.payload.response.JwtAuthResponse;
import com.example.crud.payload.response.MessageResponse;
import com.example.crud.repositories.auths.RoleRepository;
import com.example.crud.repositories.auths.UserRepository;
import com.example.crud.security.jwt.JwtUtils;
import com.example.crud.security.services.user.UserDetailsImpl;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth") //http://localhost:8080/api/user
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // maven hibernate-validator cannot be added to do javax.validation
    // if called, it will bypass javax.validation.Valid
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) {
        // authenticate user
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // get the user's roles
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtAuthResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles
                ));

    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email has already been taken"));
        }

        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username has been used"));
        }

        User user = new User(registerRequest.getUsername(), registerRequest.getFirstname(), registerRequest.getLastname(),
                registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));


        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role Not Found"));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role Not Found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Role Not Found"));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles); // set role to the user
        userRepository.save(user); // save to database

        return ResponseEntity
                .ok(new MessageResponse("User successfully registered"));
    }

    @GetMapping("/login-error")
    public ResponseEntity<?> loginErrorResponse () {
        return ResponseEntity.badRequest().body("Wrong Email or Password");
    }

    // spring boot will call this method on error when validation failed,
    // then, this method will return the json containing error message
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
