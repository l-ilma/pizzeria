package com.example.pizzeria.domain;

public class RegisterDTO {
    public String username;
    public String email;
    public String password;

    public RegisterDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
