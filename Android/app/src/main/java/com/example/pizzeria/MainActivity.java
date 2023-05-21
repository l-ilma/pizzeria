package com.example.pizzeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pizzeria.basket.BasketActivity;
import com.example.pizzeria.menu.MenuFragment;
import com.example.pizzeria.repository.AuthRepository;
import com.example.pizzeria.ui.authentication.AuthenticationActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAuth();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        loadMenuFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferencesManager.instantiate(getApplicationContext());
    }

    void loadMenuFragment() {
        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), new MenuFragment())
                .commit();
    }

    void checkAuth() {
        PreferencesManager.instantiate(getApplicationContext());
        String accessToken = PreferencesManager.getAccessToken();
        String refreshToken = PreferencesManager.getRefreshToken();
        boolean hasTokens = accessToken != null && refreshToken != null;

        if (hasTokens) {
            AuthRepository.authenticate().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    if (!response.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                    Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            startActivity(intent);
        }
    }

    public void onBasketClicked(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

//    public void onGetBackendMessageClick(View view) {
//        TextView text = findViewById(R.id.backendTestText);
//
//        BackendHelloRepository.sayHello().enqueue(new Callback<BackendHello>() {
//            @Override
//            public void onResponse(@NonNull Call<BackendHello> call, @NonNull Response<BackendHello> response) {
//                assert response.body() != null;
//                text.setText(response.body().message);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<BackendHello> call, @NonNull Throwable t) {
//                text.setText(t.getMessage());
//            }
//        });
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.settings_menu, menu);
//        return true;
//    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        PreferencesManager.clearTokens();
        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(intent);
    }
}