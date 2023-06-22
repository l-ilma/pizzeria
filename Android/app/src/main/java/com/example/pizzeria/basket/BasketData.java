package com.example.pizzeria.basket;

import com.example.pizzeria.entity.Product;

public class BasketData {
    private Product product;
    private int quantity;

    public BasketData(Product product, int quantity) {
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int val) {
        quantity = val;
    }

    public void incrementQuantity() {
        quantity += 1;
    }

    public Product getProduct() {
        return product;
    }
}
