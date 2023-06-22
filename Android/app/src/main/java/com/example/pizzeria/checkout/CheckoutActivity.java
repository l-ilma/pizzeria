package com.example.pizzeria.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.pizzeria.ui.authentication.AuthenticationActivity;
import com.example.pizzeria.utils.Status;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    BasketAdapter basketAdapter;
    EditText editTextAddress;
    EditText editTextPLZ;
    EditText editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        basketAdapter = Basket.getInstance().getAdapter();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_checkout);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPLZ = findViewById(R.id.editTextPLZ);
        editTextNote = findViewById(R.id.editNoteForDriver);

        CheckoutAdapter adapter = new CheckoutAdapter(Basket.getInstance().getUniqueBasketItems());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        showEndSum();

        createActionToolbar();

        Button checkoutButton = findViewById(R.id.orderButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderClick();
            }
        });
    }

    private void onOrderClick() {
        boolean noAddress = editTextAddress.getText().toString().equals("");
        boolean noZip = editTextPLZ.getText().toString().equals("");

        if (noAddress) {
            editTextAddress.setError("Required");
        }
        if (noZip) {
            editTextPLZ.setError("Required");
        }

        if (noAddress || noZip) {
            Toast.makeText(this, "You must provide you address and zip code", Toast.LENGTH_LONG).show();
            return;
        }

        LiveData<User> loggedInUser = StateManager.getLoggedInUser();

        if (loggedInUser.getValue() == null) {
            Toast.makeText(this, "You must log in or create an account to proceed", Toast.LENGTH_LONG).show();
            StateManager.authenticationRequested = true;
            Intent intent = new Intent(CheckoutActivity.this, AuthenticationActivity.class);
            startActivity(intent);
            return;
        } else {
            Thread finishOrderThread = new Thread(() -> {
                ProductOrderRepository productOrderRepository = new ProductOrderRepository(getApplicationContext());
                OrderRepository orderRepository = new OrderRepository(getApplicationContext());

                long orderId = orderRepository.insertOne(
                        new Order(
                                loggedInUser.getValue().id,
                                Basket.getInstance().getAdapter().getSumOfCosts(),
                                Status.ORDERED,
                                editTextAddress.getText().toString(),
                                editTextPLZ.getText().toString(),
                                editTextNote.getText().toString()
                        )
                );

                List<ProductOrder> orderProducts = new ArrayList<>();
                for (BasketData basketEntry : Basket.getInstance().getBasketItems()) {
                    orderProducts.add(new ProductOrder(orderId, basketEntry.getProduct().id, basketEntry.getQuantity()));
                    if (basketEntry.getProduct().name.equals("Custom")) {
                        CustomPizzaRepository customPizzaRepository = new CustomPizzaRepository(getApplicationContext());
                        customPizzaRepository.insertOne(new CustomPizza(basketEntry.getProduct(), loggedInUser.getValue().id));
                    }
                }

                productOrderRepository.insertAll(orderProducts);
                Basket.getInstance().emptyBasket();
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

    private void showEndSum() {
        TextView endSum = findViewById(R.id.sum);
        TextView deliveryFeeView = findViewById(R.id.delivery_costs);

        endSum.setText(String.format("%.2f", basketAdapter.getSumOfCosts()) + "€");
        deliveryFeeView.setText(basketAdapter.getDeliveryFee() + "€");
    }

    private void createActionToolbar() {
        Toolbar bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Checkout");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, BasketActivity.class));
                break;
        }
        return true;
    }
}
