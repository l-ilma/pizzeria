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
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.repository.ProductRepository;
import com.example.pizzeria.utils.Utilities;

import java.util.List;

public class PizzaMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View pizzaMenuView = inflater.inflate(R.layout.product_menu_fragment, container, false);

        ProductRepository productRepository = new ProductRepository(getContext());
        ListView pizzaList = pizzaMenuView.findViewById(R.id.productList);

        Thread setUpListViewThread = new Thread(() -> {
            List<Product> pizzaProducts = productRepository.getAllPizzas();
            pizzaList.setAdapter(new ListViewAdapter(inflater.getContext(), pizzaProducts));
        });

        setUpListViewThread.start();
        try {
            setUpListViewThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pizzaMenuView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
