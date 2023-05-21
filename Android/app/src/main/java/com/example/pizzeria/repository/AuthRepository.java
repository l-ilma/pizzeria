package com.example.pizzeria.repository;

import com.example.pizzeria.client.RetrofitClient;
import com.example.pizzeria.domain.LoggedInUser;
import com.example.pizzeria.domain.LoginDTO;
import com.example.pizzeria.domain.RegisterDTO;
import com.example.pizzeria.service.AuthService;

import retrofit2.Call;

public class AuthRepository {
    private static final AuthService authService = RetrofitClient.createService(AuthService.class);

    public static Call<LoggedInUser> register(String username, String email, String password) {
        return authService.register(new RegisterDTO(username, email, password));
    }

    public static Call<LoggedInUser> login(String email, String password) {
        return authService.login(new LoginDTO(email, password));
    }

    public static Call<LoggedInUser> refresh() {
        return authService.refresh();
    }

    public static Call<Void> authenticate() {
        return authService.authenticate();
    }
}
