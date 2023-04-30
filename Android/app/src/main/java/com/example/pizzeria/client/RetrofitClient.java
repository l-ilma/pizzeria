package com.example.pizzeria.client;

import com.example.pizzeria.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class RetrofitClient {
    private static Retrofit retrofit;

    private RetrofitClient() {}

    public static synchronized Retrofit connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Class<T> serviceClass) {
        return connect().create(serviceClass);
    }
}
