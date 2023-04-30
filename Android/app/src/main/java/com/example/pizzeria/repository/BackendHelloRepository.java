package com.example.pizzeria.repository;

import com.example.pizzeria.client.RetrofitClient;
import com.example.pizzeria.domain.BackendHello;
import com.example.pizzeria.service.BackendHelloService;

import retrofit2.Call;

public class BackendHelloRepository {
    private static final BackendHelloService helloService = RetrofitClient.createService(BackendHelloService.class);

    public static Call<BackendHello> sayHello() {
        return helloService.getHello();
    }
}
