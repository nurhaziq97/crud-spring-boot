package com.example.crud.payload.request;

import javax.validation.constraints.*;
import java.util.Set;

public class RegisterRequest {
    @NotBlank(message="Please insert username")
    private String username;

    @NotBlank(message="Please insert email")
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank(message="Please insert firstname")
    @Size(min=3)
    private String firstname;

    @NotBlank(message="Please insert lastname")
    private String lastname;

    private Set<String> role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
