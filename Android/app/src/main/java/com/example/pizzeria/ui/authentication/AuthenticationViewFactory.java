package com.example.pizzeria.ui.authentication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AuthenticationViewFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthenticationViewModel.class)) {
            return (T) new AuthenticationViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
