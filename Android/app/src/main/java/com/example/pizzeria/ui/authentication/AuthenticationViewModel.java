package com.example.pizzeria.ui.authentication;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzeria.R;
import com.example.pizzeria.domain.LoggedInUser;
import com.example.pizzeria.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationViewModel extends ViewModel {
    private final MutableLiveData<LoggedInUser> loggedInUser = new MutableLiveData<>();
    private Integer error = null;

    LiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    Integer getErrorMessage() {
        return error;
    }

    public void register(String username, String email, String password) {
        AuthRepository
                .register(username, email, password)
                .enqueue(new AuthCallback(R.string.registration_failed_error));
    }

    public void login(String email, String password) {
        AuthRepository
                .login(email, password)
                .enqueue(new AuthCallback(R.string.login_failed_error));
    }

    class AuthCallback implements Callback<LoggedInUser> {
        private final Integer errorMessage;

        AuthCallback(Integer errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void onResponse(@NonNull Call<LoggedInUser> call, @NonNull Response<LoggedInUser> response) {
            if (response.code() == 201) {
                loggedInUser.setValue(response.body());
                error = null;
            } else {
                error = errorMessage;
                loggedInUser.setValue(null);
            }
        }

        @Override
        public void onFailure(@NonNull Call<LoggedInUser> call, @NonNull Throwable t) {
            loggedInUser.setValue(null);
            error = errorMessage;
        }
    }
}

