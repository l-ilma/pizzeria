package com.example.pizzeria.models;

public class Product {
    public int id;
    public String name;
    public float price;
    public String description;

    public Product(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
