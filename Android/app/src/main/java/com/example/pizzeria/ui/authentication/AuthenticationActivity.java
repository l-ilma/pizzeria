package com.example.pizzeria.ui.authentication;

import android.content.Context;
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
import com.example.pizzeria.databinding.ActivityAuthenticationBinding;
import com.example.pizzeria.PreferencesManager;
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
        attachLoginListener();
    }

    public void onRegisterClick(View view) {
        viewModel.register(
                ((EditText) findViewById(R.id.signup_username)).getText().toString(),
                ((EditText) findViewById(R.id.signup_email)).getText().toString(),
                ((EditText) findViewById(R.id.signup_password)).getText().toString()
        );
    }

    public void onLoginClick(View view) {
        viewModel.login(
                ((EditText) findViewById(R.id.login_email)).getText().toString(),
                ((EditText) findViewById(R.id.login_password)).getText().toString()
        );
    }

    private void attachLoginListener() {
        viewModel.getLoggedInUser().observe(this, user -> {
            if (user == null) {
                Toast.makeText(getApplicationContext(), viewModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            PreferencesManager.instantiate(getApplicationContext());
            PreferencesManager.saveAuthTokens(user.accessToken, user.refreshToken);
            finish();
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
