package com.example.pizzeria.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import com.example.pizzeria.R;
import com.example.pizzeria.StateManager;
import com.example.pizzeria.databinding.ActivityAdminBinding;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.UserRepository;
import com.example.pizzeria.ui.authentication.AuthenticationActivity;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding layoutBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        setSupportActionBar(layoutBinding.toolbar);

        new OrderRepository(getApplicationContext()).loadAllOrdersWithProducts().observe(this, list -> {
            layoutBinding.orderList.setAdapter(new OrderArrayAdapter(this, list));
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.only_logout_menu, menu);

        if (menu instanceof MenuBuilder) {
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
        AsyncTask.execute(() -> {
            new UserRepository(getApplicationContext()).logout();
            StateManager.setLoggedInUser(null);
        });
        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(intent);
    }
}