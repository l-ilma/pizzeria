package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pizzeria.basket.BasketActivity;
import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.entity.ProductOrder;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.history.OrderListAdapter;
import com.example.pizzeria.history.OrderWithProducts;
import com.example.pizzeria.menu.MenuFragment;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.ProductOrderRepository;
import com.example.pizzeria.repository.ProductRepository;
import com.example.pizzeria.repository.UserRepository;
import com.example.pizzeria.ui.authentication.AuthenticationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public OrderListAdapter orderListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doLoggedInUserLookup();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        loadMenuFragment();
    }



    private void doLoggedInUserLookup() {
        AsyncTask.execute(() -> {
            User loggedInUser = new UserRepository(getApplicationContext()).getLoggedInUser();
            StateManager.setLoggedInUser(loggedInUser);
            if (loggedInUser == null) {
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });
    }

    void loadMenuFragment() {
        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), new MenuFragment())
                .commit();
    }

    public void onBasketClicked(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            hideLogoutIfGuest(m);
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    private void getUserOrderHistory(){
        OrderRepository orderRepository = new OrderRepository(getApplicationContext());
        ProductOrderRepository productOrderRepository = new ProductOrderRepository(getApplicationContext());
        ProductRepository productRepository = new ProductRepository(getApplicationContext());

        List<Order> orders = orderRepository.getUserOrders(StateManager.getLoggedInUser().getValue().id);

        List<OrderWithProducts> ordersWithProducts = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for(Order order : orders){
            List<Long> productIds = productOrderRepository.getProductsForOrder(order.id);
            for(Long id : productIds){
                Product product = productRepository.getProduct(id);
                products.add(product);
            }

            ordersWithProducts.add(new OrderWithProducts(order, products));
        }

        orderListAdapter = new OrderListAdapter(ordersWithProducts);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.orders){

        }
        else if(item.getItemId() == R.id.my_pizzas){

        }
        else if (item.getItemId() == R.id.logout && StateManager.getLoggedInUser() != null) {
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
}