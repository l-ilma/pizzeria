package com.example.pizzeria.domain;

public class User {
    public String id;

    public String username;

    public String email;

    public String profileImagePath;

    public String title;


    public User(String id, String username, String email, String profileImagePath, String title) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImagePath = profileImagePath;
        this.title = title;
    }
}
