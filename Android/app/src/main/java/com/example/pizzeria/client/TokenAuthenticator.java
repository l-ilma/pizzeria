package com.example.pizzeria.client;

import androidx.annotation.NonNull;

import com.example.pizzeria.PreferencesManager;
import com.example.pizzeria.domain.LoggedInUser;
import com.example.pizzeria.repository.AuthRepository;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Response;

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, @NonNull okhttp3.Response response) {
        Call<LoggedInUser> refreshCall = AuthRepository.refresh();

        try {
            Response<LoggedInUser> res = refreshCall.execute();
            if (res.isSuccessful()) {
                if (res.body() != null) {
                    PreferencesManager.saveAuthTokens(res.body().accessToken, res.body().refreshToken);
                    return response.request().newBuilder()
                            .header("Authorization", "Bearer " + PreferencesManager.getAccessToken())
                            .build();
                }
            } else {
                throw new Exception();
            }

            return null;
        } catch (Exception e) {
            PreferencesManager.saveAuthTokens(null, null);
            return null;
        }
//        AuthRepository.refresh().enqueue(new Callback<LoggedInUser>() {
//            @Override
//            public void onResponse(@NonNull Call<LoggedInUser> call, @NonNull retrofit2.Response<LoggedInUser> res) {
//                if (!res.isSuccessful()) {
//                    PreferencesManager.saveAuthTokens(null, null);
//                    return;
//                }
//
//                if (res.body() != null)
//                    PreferencesManager.saveAuthTokens(res.body().accessToken, res.body().refreshToken);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<LoggedInUser> call, @NonNull Throwable t) {
//                PreferencesManager.saveAuthTokens(null, null);
//            }
//        });
    }
}
