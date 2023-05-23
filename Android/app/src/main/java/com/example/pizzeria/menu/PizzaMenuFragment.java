package com.example.pizzeria.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pizzeria.ListViewAdapter;
import com.example.pizzeria.R;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.utils.Utilities;

import java.util.List;

public class PizzaMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View pizzaMenuView = inflater.inflate(R.layout.product_menu_fragment, container, false);
        ListView pizzaList = pizzaMenuView.findViewById(R.id.productList);

        List<Product> pizzaProducts = Utilities.getPizzaProducts();
        pizzaList.setAdapter(new ListViewAdapter(inflater.getContext(), pizzaProducts));

        return pizzaMenuView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
