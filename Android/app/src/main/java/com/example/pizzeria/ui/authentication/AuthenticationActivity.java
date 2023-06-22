package com.example.pizzeria.ui.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pizzeria.MainActivity;
import com.example.pizzeria.R;
import com.example.pizzeria.StateManager;
import com.example.pizzeria.checkout.CheckoutActivity;
import com.example.pizzeria.databinding.ActivityAuthenticationBinding;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.repository.UserRepository;
import com.example.pizzeria.ui.admin.AdminActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AuthenticationActivity extends AppCompatActivity {

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthenticationBinding viewBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        doLoggedInUserLookup();

        assembleCredentialsPager();
    }

    private void doLoggedInUserLookup() {
        AsyncTask.execute(() -> {
            User loggedInUser = new UserRepository(getApplicationContext()).getLoggedInUser();
            StateManager.setLoggedInUser(loggedInUser);
            if (loggedInUser == null) return;

            if (loggedInUser.isAdmin) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onRegisterClick(View view) {
        if (!isValidRegisterInput()) return;

        String username = ((EditText) findViewById(R.id.signup_username)).getText().toString();
        String email = ((EditText) findViewById(R.id.signup_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.signup_password)).getText().toString();

        AsyncTask.execute(() -> {

            try {
                User user = new UserRepository(getApplicationContext()).register(
                        username,
                        email,
                        password
                );
                StateManager.setLoggedInUser(user);
                redirectToActivityBasedOnRole(user);
            } catch (Exception e) {
                this.runOnUiThread(() ->
                        Toast.makeText(this, R.string.registration_failed_error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    public void onLoginClick(View view) {
        if (!isValidLoginInput()) return;

        AsyncTask.execute(() -> {
            try {
                User user = new UserRepository(getApplicationContext()).login(
                        ((EditText) findViewById(R.id.login_email)).getText().toString(),
                        ((EditText) findViewById(R.id.login_password)).getText().toString()
                );
                StateManager.setLoggedInUser(user);
                redirectToActivityBasedOnRole(user);
            } catch (Exception e) {
                this.runOnUiThread(() ->
                        Toast.makeText(this, R.string.authentication_failed_error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    public void onContinueAsGuestInLoginClick(View view) {
        StateManager.setLoggedInUser(null);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void assembleCredentialsPager() {
        ViewPager2 viewPager = findViewById(R.id.credentials_pager);
        TabLayout tabLayout = findViewById(R.id.credentials_tab_layout);

        AuthenticationMenuAdapter adapter = new AuthenticationMenuAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(position == 0 ? R.string.login : R.string.register)).attach();
    }

    private boolean isValidLoginInput() {
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);

        boolean valid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Invalid email address");
            valid = false;
        }

        if (password.toString().isEmpty()) {
            password.setError("Password cannot be empty");
            valid = false;
        }

        return valid;
    }

    private boolean isValidRegisterInput() {
        boolean valid = true;

        EditText username = findViewById(R.id.signup_username);
        EditText email = findViewById(R.id.signup_email);
        EditText password = findViewById(R.id.signup_password);

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Invalid email address");
            valid = false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Password cannot be empty");
            valid = false;
        }

        if (username.getText().toString().isEmpty()) {
            username.setError("Username cannot be empty");
            valid = false;
        }

        return valid;
    }

    private void redirectToActivityBasedOnRole(User user) {
        if (user.isAdmin) {
            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        } else {
            if (StateManager.authenticationRequested) {
                startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
                StateManager.authenticationRequested = false;
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }
}
