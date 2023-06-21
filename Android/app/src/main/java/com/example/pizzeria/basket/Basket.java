package com.example.pizzeria.basket;

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

    public void updateItemCount(BasketData item, int count) {
        basketContent.stream().filter(i -> i.getProduct().name.equals(item.getProduct().name))
                .findFirst()
                .ifPresent(i -> i.setQuantity(count));
    }

    public void removeItem(BasketData item) {
        basketContent.remove(item);
    }

    public void setAdapter(BasketAdapter _adapter){this.adapter = _adapter;}

    public BasketAdapter getAdapter(){return this.adapter;}
}
