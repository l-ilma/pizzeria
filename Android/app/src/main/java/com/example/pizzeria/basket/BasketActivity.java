package com.example.pizzeria.basket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.Checkout.CheckoutActivity;
import com.example.pizzeria.R;

import java.util.Objects;

public class BasketActivity extends AppCompatActivity {

    private BasketAdapter adapter;
    private TextView sumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new BasketAdapter(Basket.getInstance().getBasketItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        TextView deliveryFeeView = findViewById(R.id.delivery_costs);
        sumView = findViewById(R.id.sum);

        deliveryFeeView.setText(adapter.getDeliveryFee() + "€");
        sumView.setText(String.format("%.2f", adapter.getSumOfCosts()) + "€");

        setupActionBar();
    }

    public void removeItem(int position) {
        adapter.removeItem(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, Basket.getInstance().getBasketItems().length);
        refreshSumView();
    }

    public void refreshSumView() {
        sumView.setText(String.format("%.2f", adapter.getSumOfCosts()) + "€");
    }

    public void changeItemCount(int position, int quantity) {
        adapter.changeItemCount(position, quantity);
        refreshSumView();
    }

    public void onCheckoutClicked(View view) {
        // TODO
        Intent intent = new Intent(BasketActivity.this, CheckoutActivity.class);
        startActivity(intent);
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Your Basket");
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
