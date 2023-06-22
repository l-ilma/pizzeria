
package com.example.pizzeria.Checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pizzeria.MainActivity;
import com.example.pizzeria.R;
import com.example.pizzeria.StateManager;
import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketActivity;
import com.example.pizzeria.basket.BasketAdapter;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.CustomPizza;
import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.ProductOrder;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.repository.CustomPizzaRepository;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.ProductOrderRepository;

import java.util.ArrayList;
import java.util.List;


public class CheckoutActivity extends AppCompatActivity {

    private CheckoutAdapter adapter;
    private TextView EndSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_checkout);

        adapter = new CheckoutAdapter(Basket.getInstance().getBasketItems());
        adapter.setBasketAdapter(Basket.getInstance().getAdapter());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        showEndSum();

        createActionToolbar();

        Button checkoutButton = findViewById(R.id.buy_now_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBuyClick();
            }
        });
        //updateProgressBar();
    }

    private void onBuyClick() {

        LiveData<User> loggedInUser = StateManager.getLoggedInUser();
        if(loggedInUser != null){
            Thread finishOrderThread = new Thread(() -> {
                ProductOrderRepository productOrderRepository = new ProductOrderRepository(getApplicationContext());
                OrderRepository orderRepository = new OrderRepository(getApplicationContext());

                long orderId = orderRepository.insertOne(new Order(loggedInUser.getValue().id,
                        Basket.getInstance().getAdapter().getSumOfCosts()));

                List<ProductOrder> orderProducts = new ArrayList<>();
                for(BasketData basketEntry : Basket.getInstance().getBasketItems()){
                    orderProducts.add(new ProductOrder(orderId, basketEntry.getProduct().id));
                    if(basketEntry.getProduct().name.equals("Custom")){
                        CustomPizzaRepository customPizzaRepository = new CustomPizzaRepository(getApplicationContext());
                        customPizzaRepository.insertOne(new CustomPizza(basketEntry.getProduct(), loggedInUser.getValue().id));
                    }
                }

                productOrderRepository.insertAll(orderProducts);
            });

            finishOrderThread.start();
            try {
                finishOrderThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast orderMadeToast = Toast.makeText(getApplicationContext(), "Thank you for your order," +
                " it will be delivered as soon as possible", Toast.LENGTH_LONG);
        orderMadeToast.show();

        Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showEndSum(){
        EndSum = findViewById(R.id.sum_total);
        BasketAdapter basket_adapter =  Basket.getInstance().getAdapter();

        EndSum.setText(String.format("%.2f", basket_adapter.getSumOfCosts()) + "€");
    }

    private void createActionToolbar(){
        Toolbar bar = findViewById(R.id.toolbar_checkout);
        setSupportActionBar(bar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Checkout");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }





}
