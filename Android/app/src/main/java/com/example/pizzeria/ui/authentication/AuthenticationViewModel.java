package com.example.pizzeria.ui.authentication;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzeria.R;

public class AuthenticationViewModel extends ViewModel {
    private MutableLiveData<Integer> validationError = new MutableLiveData<>(null);

    LiveData<Integer> getValidationError() {
        return validationError;
    }

    public void setValidationError(Integer value) {
        validationError.postValue(value);
    }

    public void validateRegister(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            setValidationError(R.string.validation_empty);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setValidationError(R.string.validation_invalid_email);
            return;
        }

        if (password.length() < 8) {
            setValidationError(R.string.validation_invalid_password);
        }
    }
}

