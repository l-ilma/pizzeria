package com.example.pizzeria.basket;

public class BasketData {
    private final String description;
    private final int imgId;
    private int quantity;
    private final float price;

    public BasketData(String description, int imgId, int quantity, float price) {
        this.description = description;
        this.imgId = imgId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getImgId() {
        return imgId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int val) {
        quantity = val;
    }

    public float getPrice() {
        return price;
    }

}
