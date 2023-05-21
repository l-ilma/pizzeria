package com.example.pizzeria;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

public class PreferencesManager {
    private static PreferencesManager instance;
    private static WeakReference<Context> context;
    private static final String USER_PREFERENCES = "user";
    private static final String ACCESS_TOKEN_KEY = "at";
    private static final String REFRESH_TOKEN_KEY = "rt";


    private PreferencesManager(Context context) {
        PreferencesManager.context = new WeakReference<>(context);
    }

    public static synchronized void instantiate(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context);
        }
    }

    public static void saveAuthTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = context
                .get()
                .getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        editor.apply();
    }

    public static String getAccessToken() {
        SharedPreferences preferences = context
                .get()
                .getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public static String getRefreshToken() {
        SharedPreferences preferences = context
                .get()
                .getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(REFRESH_TOKEN_KEY, null);
    }
}
