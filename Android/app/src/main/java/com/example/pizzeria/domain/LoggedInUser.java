package com.example.pizzeria.domain;

public class LoggedInUser extends User {
    public String accessToken;
    public String refreshToken;

    public LoggedInUser(String id, String username, String email, String profileImagePath, String title, String accessToken, String refreshToken) {
        super(id, username, email, profileImagePath, title);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
