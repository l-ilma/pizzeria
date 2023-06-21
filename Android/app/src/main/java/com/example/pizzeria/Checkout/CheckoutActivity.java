
package com.example.pizzeria.Checkout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.pizzeria.R;
import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketAdapter;
import com.example.pizzeria.basket.BasketData;


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
        //updateProgressBar();
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
