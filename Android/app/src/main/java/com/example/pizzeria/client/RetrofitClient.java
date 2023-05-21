package com.example.pizzeria.client;

import com.example.pizzeria.BuildConfig;
import com.example.pizzeria.PreferencesManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class RetrofitClient {
    private static Retrofit retrofit;

    private RetrofitClient() {}

    public static synchronized Retrofit connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_API_URL)
                    .client(createHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder().authenticator(new TokenAuthenticator()).addInterceptor(chain -> {
            String relevantToken = chain.request().url().encodedPath().equals("/auth/refresh")
                    ? PreferencesManager.getRefreshToken()
                    : PreferencesManager.getAccessToken();

            if (relevantToken == null) return chain.proceed(chain.request());

            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + relevantToken)
                    .build();

            return chain.proceed(newRequest);
        }).build();
    }

    public static <T> T createService(Class<T> serviceClass) {
        return connect().create(serviceClass);
    }
}
