package com.example.pizzeria.utils;

import com.example.pizzeria.R;
import com.example.pizzeria.entity.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    public static List<Float> pizzaPrices = new ArrayList(Arrays.asList(6.99f, 7.99f, 8.99f,
                                                            9.99f, 10.99f, 11.99f, 12.49f, 13.49f));
    public static List<String> pizzaNames = new ArrayList(Arrays.asList("Margeritha", "Cappriociosa",
                                                                        "Funghi", "Rucola",
                                                                        "Buffalina", "Ananas",
                                                                        "Tonno", "Custom"));
    public static List<Integer> pizzaImages = new ArrayList(Arrays.asList(
            R.drawable.pizza_margeritha,
            R.drawable.pizza_capriciossa,
            R.drawable.pizza_funghi,
            R.drawable.pizza_rucola,
            R.drawable.pizza_buffalina,
            R.drawable.pizza_ananas,
            R.drawable.pizza_tonno,
            R.drawable.pizza_tonno
    ));

    public static List<Float> drinkPrices = new ArrayList(Arrays.asList(1.99f, 1.99f, 1.99f, 1.99f,
                                                                             2.49f, 3.49f, 5.99f));
    public static List<String> drinkNames = new ArrayList(Arrays.asList("Water", "Fanta",
                                                                        "Coca Cola", "Pepsi",
                                                                        "Cocta", "Beer", "Wine"));
    public static List<Integer> drinkImages = new ArrayList(Arrays.asList(
            R.drawable.drink_water,
            R.drawable.drink_fanta,
            R.drawable.drink_cola,
            R.drawable.drink_pepse,
            R.drawable.drink_cocta,
            R.drawable.drink_beer,
            R.drawable.drink_wine
    ));

    public static List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        for(int position = 0; position < pizzaPrices.size(); position++ ){
            products.add(new Product(pizzaNames.get(position),
                    pizzaPrices.get(position), pizzaImages.get(position), "", 1));
        }

        for(int position = 0; position < drinkPrices.size(); position++ ){
            products.add(new Product(drinkNames.get(position),
                    drinkPrices.get(position), drinkImages.get(position), "", 0));
        }

        return products;
    }
}
