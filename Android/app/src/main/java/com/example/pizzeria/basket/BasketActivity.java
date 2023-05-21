package com.example.pizzeria.basket;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;

public class BasketActivity extends AppCompatActivity {

    private BasketAdapter adapter;
    private TextView sumView;

    BasketData[] basketData = new BasketData[] {
            new BasketData("Cheese Pizza", R.drawable.cheese_pizza, 1, 10.4f),
            new BasketData("Veggie Pizza", R.drawable.cheese_pizza, 2, 9.2f),
            new BasketData("Pepperoni Pizza", R.drawable.cheese_pizza, 2, 10f),
            new BasketData("Meat Pizza", R.drawable.cheese_pizza, 1, 12.2f),
            new BasketData("Margherita Pizza", R.drawable.cheese_pizza, 3, 8.4f)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new BasketAdapter(basketData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        TextView deliveryFeeView = findViewById(R.id.delivery_costs);
        sumView = findViewById(R.id.sum);

        deliveryFeeView.setText(adapter.getDeliveryFee() + "€");
        sumView.setText(String.format("%.2f", adapter.getSumOfCosts()) + "€");
    }

    public void removeItem(int position) {
        adapter.removeItem(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, basketData.length);
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
    }
}
