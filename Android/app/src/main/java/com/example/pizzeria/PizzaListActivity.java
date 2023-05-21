package com.example.pizzeria;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PizzaListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        ListView pizzaList = findViewById(R.id.pizzaList);
        List<Integer> pizzas = new ArrayList<>();
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzas.add(R.drawable.ic_pizza_mozzarela);
        pizzaList.setAdapter(new PizzaArrayAdapter(getApplicationContext(), pizzas));
    }
}
