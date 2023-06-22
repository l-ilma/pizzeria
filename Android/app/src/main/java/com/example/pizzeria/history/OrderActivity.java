package com.example.pizzeria.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.MainActivity;
import com.example.pizzeria.R;
import com.example.pizzeria.StateManager;
import com.example.pizzeria.basket.BasketActivity;
import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.ProductOrderRepository;
import com.example.pizzeria.repository.ProductRepository;
import com.example.pizzeria.repository.UserRepository;
import com.example.pizzeria.ui.authentication.AuthenticationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    private OrderListAdapter orderListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Thread getOrderHistoryThread = new Thread(() -> {
            getUserOrderHistory();
        });

        getOrderHistoryThread.start();
        try {
            getOrderHistoryThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        RecyclerView orderHistoryView = findViewById(R.id.order_card_view);
        orderHistoryView.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryView.setAdapter(orderListAdapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            hideLogoutIfGuest(m);
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void hideLogoutIfGuest(MenuBuilder m) {
        if (StateManager.getLoggedInUser().getValue() == null) {
            MenuItem logoutMenuItem = m.findItem(R.id.logout);
            logoutMenuItem.setVisible(false);
            MenuItem ordersMenuItem = m.findItem(R.id.orders);
            ordersMenuItem.setVisible(false);
            MenuItem myPizzasMenuItem = m.findItem(R.id.my_pizzas);
            myPizzasMenuItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.orders){
            Intent intent = new Intent(OrderActivity.this, OrderActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.my_pizzas) {

        } else if (item.getItemId() == R.id.logout && StateManager.getLoggedInUser() != null) {
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

    public void onBasketClicked(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    private void getUserOrderHistory(){
        OrderRepository orderRepository = new OrderRepository(getApplicationContext());
        orderListAdapter = new OrderListAdapter(getApplicationContext(), orderRepository.loadAllOrdersWithProductsStatic());
    }

}
