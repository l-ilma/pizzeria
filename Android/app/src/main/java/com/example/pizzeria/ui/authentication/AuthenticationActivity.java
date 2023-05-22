package com.example.pizzeria.ui.authentication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pizzeria.R;
import com.example.pizzeria.StateManager;
import com.example.pizzeria.databinding.ActivityAuthenticationBinding;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.repository.UserRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AuthenticationActivity extends AppCompatActivity {
    private AuthenticationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new AuthenticationViewFactory()).get(AuthenticationViewModel.class);
        ActivityAuthenticationBinding viewBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        assembleCredentialsPager();
        attachValidationListener();
    }

    public void onRegisterClick(View view) {
        String username = ((EditText) findViewById(R.id.signup_username)).getText().toString();
        String email = ((EditText) findViewById(R.id.signup_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.signup_password)).getText().toString();
        viewModel.validateRegister(username, email, password);

        AsyncTask.execute(() -> {
            if (viewModel.getValidationError().getValue() != null) {
                return;
            }

            try {
                User user = new UserRepository(getApplicationContext()).register(
                        username,
                        email,
                        password
                );
                StateManager.setLoggedInUser(user);
                if (viewModel.getValidationError().getValue() == null && user != null) {
                    finish();
                }
            } catch (Exception e) {
                viewModel.setValidationError(R.string.registration_failed_error);
            }
        });
    }

    public void onLoginClick(View view) {
        AsyncTask.execute(() -> {
            try {
                User user = new UserRepository(getApplicationContext()).login(
                        ((EditText) findViewById(R.id.login_email)).getText().toString(),
                        ((EditText) findViewById(R.id.login_password)).getText().toString()
                );
                StateManager.setLoggedInUser(user);
                finish();
            } catch (Exception e) {
                viewModel.setValidationError(R.string.authentication_failed_error);
            }
        });
    }

    private void attachValidationListener() {
        viewModel.getValidationError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void assembleCredentialsPager() {
        ViewPager2 viewPager = findViewById(R.id.credentials_pager);
        TabLayout tabLayout = findViewById(R.id.credentials_tab_layout);

        AuthenticationMenuAdapter adapter = new AuthenticationMenuAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(position == 0 ? R.string.login : R.string.register)).attach();
    }
}
