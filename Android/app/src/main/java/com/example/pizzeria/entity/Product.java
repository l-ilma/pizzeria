package com.example.pizzeria.entity;

import androidx.room.Entity;

@Entity
public class Product extends BaseEntity{
    public String name;
    public float price;
    public int staticId;
    public String ingredients;
    public int pizza;

    public Product(String name, float price, int staticId, String ingredients, int pizza) {
        this.name = name;
        this.price = price;
        this.staticId = staticId;
        this.ingredients = ingredients;
        this.pizza = pizza;
    }
}
