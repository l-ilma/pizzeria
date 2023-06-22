package com.example.pizzeria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzeria.entity.User;

public class StateManager {
    private static final MutableLiveData<User> loggedInUser = new MutableLiveData<>(null);

    public static boolean authenticationRequested = false;

    public static void setLoggedInUser(User user) {
        loggedInUser.postValue(user);
    }

    public static LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }
}
