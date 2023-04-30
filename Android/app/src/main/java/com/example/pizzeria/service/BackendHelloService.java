package com.example.pizzeria.service;

import com.example.pizzeria.domain.BackendHello;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendHelloService {
    @GET("/")
    Call<BackendHello> getHello();
}
