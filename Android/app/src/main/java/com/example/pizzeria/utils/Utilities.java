package com.example.pizzeria.utils;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.model.OrderWithProducts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class Utilities {
    public static List<Float> pizzaPrices = new ArrayList(Arrays.asList(6.99f, 7.99f, 8.99f,
            9.99f, 10.99f, 11.99f, 12.49f, 13.49f));
    public static List<String> pizzaNames = new ArrayList(Arrays.asList("Margherita", "Capricciosa",
            "Funghi", "Rucola",
            "Bufalina", "Hawaii",
            "Tonno", "Custom"));
    public static List<Integer> pizzaImages = new ArrayList(Arrays.asList(
            R.drawable.pizza_margherita,
            R.drawable.pizza_capriciossa,
            R.drawable.pizza_funghi,
            R.drawable.pizza_rucola,
            R.drawable.pizza_buffalina,
            R.drawable.pizza_ananas,
            R.drawable.pizza_tonno,
            R.drawable.pizza_custom
    ));

    public static List<String> ingredients = new ArrayList<>(
            Arrays.asList("Cheese",
                    "Pepperoni",
                    "Mushrooms",
                    "Onions",
                    "Bell peppers",
                    "Olives",
                    "Basil",
                    "Garlic",
                    "Oregano",
                    "Spinach",
                    "Sausage",
                    "Anchovies",
                    "Parmesan cheese",
                    "Ham",
                    "Pineapple",
                    "Jalapenos",
                    "Bacon",
                    "Artichokes",
                    "Feta cheese",
                    "Sun-dried tomatoes",
                    "Chicken",
                    "Ricotta cheese",
                    "Provolone cheese",
                    "Salami",
                    "Red onions",
                    "Green olives",
                    "Roasted garlic",
                    "Mozzarella cheese",
                    "Pesto sauce",
                    "Cherry tomatoes",
                    "Balsamic glaze"));

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

    static String margheritaIngredients = "Mozzarella cheese, basil";
    static String cappriociosaIngredients = "Mozzarella cheese, baked ham, mushrooms, artichokes";
    static String funghiIngredients = "Mozzarella cheese, mushrooms";
    static String rucolaIngredients = "Arugula, prosciutto, parmesan cheese, olive oil";
    static String bufalinaIngredients = "Cherry tomatoes, buffalo mozzarella, fresh basil leaves";
    static String hawaiiIngredients = "Mozzarella cheese, cooked ham, pineapple";
    static String tonnoIngredients = "Tuna, red onions, black olives, olive oil";

    static List<String> pizzaToppings = new ArrayList<>(Arrays.asList(margheritaIngredients, cappriociosaIngredients, funghiIngredients,
            rucolaIngredients, bufalinaIngredients, hawaiiIngredients, tonnoIngredients));

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (int position = 0; position < pizzaPrices.size(); position++) {
            String toppings = Objects.equals(pizzaNames.get(position), "Custom") ?
                    "" : pizzaToppings.get(position);
            products.add(new Product(pizzaNames.get(position),
                    pizzaPrices.get(position), pizzaImages.get(position), toppings, 1));
        }

        for (int position = 0; position < drinkPrices.size(); position++) {
            products.add(new Product(drinkNames.get(position),
                    drinkPrices.get(position), drinkImages.get(position), "", 0));
        }

        return products;
    }

    public static String getProductsAsString(OrderWithProducts orderWithProducts) {
        HashMap<String, Integer> productCountMap = new HashMap<>();

        for (Product p : orderWithProducts.products) {
            if (productCountMap.containsKey(p.name)) {
                productCountMap.put(p.name, productCountMap.get(p.name) + 1);
            } else {
                productCountMap.put(p.name, 1);
            }
        }

        StringJoiner productStringJoiner = new StringJoiner(", ");
        for (Map.Entry entry : productCountMap.entrySet()) {
            productStringJoiner.add(String.format("%s %dx", entry.getKey(), entry.getValue()));
        }

        return productStringJoiner.toString();
    }

    public static HashMap<String, Integer> getProductCounts(OrderWithProducts orderWithProducts){
        HashMap<String, Integer> productCountMap = new HashMap<>();

        for (Product p : orderWithProducts.products) {
            if (productCountMap.containsKey(p.name)) {
                productCountMap.put(p.name, productCountMap.get(p.name) + 1);
            } else {
                productCountMap.put(p.name, 1);
            }
        }

        return productCountMap;
    }
}
