package com.example.pizzeria.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pizzeria.PizzaArrayAdapter;
import com.example.pizzeria.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PizzaMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View pizzaMenuView = inflater.inflate(R.layout.pizza_menu_fragment, container, false);
        ListView pizzaList = pizzaMenuView.findViewById(R.id.pizzaList);

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

        pizzaList.setAdapter(new PizzaArrayAdapter(inflater.getContext(), pizzas));
        return pizzaMenuView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
