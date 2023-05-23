package com.example.pizzeria.utils;

import com.example.pizzeria.R;
import com.example.pizzeria.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    //, 12.99, 13.99, 14.99, 15.99, 16.99
    public static List<Float> pizzaPrices = new ArrayList(Arrays.asList(6.99f, 7.99f, 8.99f,
                                                            9.99f, 10.99f, 11.99f, 12.49f));
    public static List<String> pizzaNames = new ArrayList(Arrays.asList("Margeritha", "Cappriociosa",
                                                                        "Funghi", "Rucola",
                                                                        "Buffalina", "Ananas",
                                                                        "Tonno" ));
    public static List<Integer> pizzaImages = new ArrayList(Arrays.asList(
            R.drawable.pizza_margeritha,
            R.drawable.pizza_capriciossa,
            R.drawable.pizza_funghi,
            R.drawable.pizza_rucola,
            R.drawable.pizza_buffalina,
            R.drawable.pizza_ananas,
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

    public static List<Product> getPizzaProducts() {
        List<Product> pizzaProducts = new ArrayList<>();
        for(int position = 0; position < pizzaPrices.size(); position++ ){
            pizzaProducts.add(new Product(pizzaImages.get(position), pizzaNames.get(position),
                    pizzaPrices.get(position)));
        }

        return pizzaProducts;
    }

    public static List<Product> getDrinkProducts() {
        List<Product> drinkProducts = new ArrayList<>();
        for(int position = 0; position < drinkPrices.size(); position++ ){
            drinkProducts.add(new Product(drinkImages.get(position), drinkNames.get(position),
                    drinkPrices.get(position)));
        }

        return drinkProducts;
    }
}
