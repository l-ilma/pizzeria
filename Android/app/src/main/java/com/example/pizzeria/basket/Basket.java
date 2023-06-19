package com.example.pizzeria.basket;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private static Basket basket;
    private Map<Integer, BasketData> basketContent = new HashMap();

    private BasketAdapter adapter;
    private Basket(){
    }

    public static Basket getInstance(){
        if(basket == null){
            basket = new Basket();
        }

        return basket;
    }

    public void addItem(BasketData basketItem) {
        if(basketContent.containsKey(basketItem.getImgId())) {
            basketContent.get(basketItem.getImgId()).incrementQuantity();
        }
        else {
            basketContent.put(basketItem.getImgId(), basketItem);
        }
    }

    public BasketData[] getBasketItems(){
        return basketContent.values().toArray(new BasketData[basketContent.values().size()]);
    }

    public void updateItemCount(int id, int count) {
        basketContent.get(id).setQuantity(count);
    }

    public void removeItem(int id) {
        basketContent.remove(id);
    }

    public void setAdapter(BasketAdapter _adapter){this.adapter = _adapter;}

    public BasketAdapter getAdapter(){return this.adapter;}
}
