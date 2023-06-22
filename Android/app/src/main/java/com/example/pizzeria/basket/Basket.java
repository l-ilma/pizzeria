package com.example.pizzeria.basket;

import com.example.pizzeria.entity.Product;
import com.example.pizzeria.utils.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {
    private static Basket basket;

    private BasketAdapter adapter;
    private final List<BasketData> basketContent = new ArrayList<>();
    private Basket(){
    }

    public static Basket getInstance(){
        if(basket == null){
            basket = new Basket();
        }

        return basket;
    }

    public void addItem(BasketData basketItem) {
        List<BasketData> existingItems =
                basketContent.stream().filter(i -> i.getProduct().name.equals(basketItem.getProduct().name))
                        .collect(Collectors.toList());
        if (existingItems.isEmpty()) {
            basketContent.add(basketItem);
        } else {
            BasketData found = null;
            for (BasketData existingItem: existingItems) {
                if (existingItem.getProduct().name.equals("Custom")) {
                    if (ListUtils.listEqualsIgnoreOrder(
                            Arrays.asList(basketItem.getProduct().ingredients.split(",")),
                            Arrays.asList(existingItem.getProduct().ingredients.split(",")))
                    ) {
                        found = existingItem;
                        break;
                    }
                } else {
                    if (existingItem.getProduct().name.equals(basketItem.getProduct().name)) {
                        found = existingItem;
                        break;
                    }
                }
            }

            if (found != null) {
                found.incrementQuantity();
            } else {
                basketContent.add(basketItem);
            }
        }
    }

    public BasketData[] getBasketItems(){
        return basketContent.toArray(new BasketData[0]);
    }

    public BasketData[] getUniqueBasketItems() { // all custom pizzas are considered the same
        List<BasketData> uniqueBasketData = new ArrayList<>();
        int customPizzasCount = 0;
        float customPizzaPrice = 0f;
        int customPizzaId = 0;
        for (BasketData basketItem: basketContent) {
            if (basketItem.getProduct().name.equals("Custom")) {
                customPizzasCount += basketItem.getQuantity();
                customPizzaId = basketItem.getProduct().staticId;
                customPizzaPrice = basketItem.getProduct().price;
            } else {
                uniqueBasketData.add(basketItem);
            }
        }
        if (customPizzasCount > 0) {
            uniqueBasketData.add(new BasketData(
                    new Product("Custom", customPizzaPrice, customPizzaId, "", 1),
                    customPizzasCount));
        }
        return uniqueBasketData.toArray(new BasketData[0]);
    }

    public void updateItemCount(int index, int count) {
        basketContent.get(index).setQuantity(count);
    }

    public void removeItem(BasketData item) {
        basketContent.remove(item);
    }

    public void emptyBasket() {
        basketContent.clear();
    }

    public void setAdapter(BasketAdapter _adapter){this.adapter = _adapter;}

    public BasketAdapter getAdapter(){return this.adapter;}
}
