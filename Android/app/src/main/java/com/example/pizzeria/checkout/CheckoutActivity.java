package com.example.pizzeria.checkout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketAdapter;

public class CheckoutActivity extends AppCompatActivity {
    BasketAdapter basketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        basketAdapter =  Basket.getInstance().getAdapter();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_checkout);

        CheckoutAdapter adapter = new CheckoutAdapter(Basket.getInstance().getBasketItems());
        adapter.setBasketAdapter(Basket.getInstance().getAdapter());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        showEndSum();

        createActionToolbar();
    }

    private void showEndSum(){
        TextView endSum = findViewById(R.id.sum);
        TextView deliveryFeeView = findViewById(R.id.delivery_costs);

        endSum.setText(String.format("%.2f", basketAdapter.getSumOfCosts()) + "€");
        deliveryFeeView.setText(basketAdapter.getDeliveryFee() + "€");    }

    private void createActionToolbar(){
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
                onBackPressed();
                break;
        }
        return true;
    }
}
