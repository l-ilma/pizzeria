package com.example.pizzeria.service;

import com.example.pizzeria.domain.LoggedInUser;
import com.example.pizzeria.domain.LoginDTO;
import com.example.pizzeria.domain.RegisterDTO;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/register")
    Call<LoggedInUser> register(@Body RegisterDTO registerDTO);

    @POST("/auth/login")
    Call<LoggedInUser> login(@Body LoginDTO loginDTO);

    @POST("/auth/refresh")
    Call<LoggedInUser> refresh();

    @GET("/auth/authenticate")
    Call<Void> authenticate();
}
