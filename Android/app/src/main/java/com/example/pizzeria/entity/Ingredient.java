package com.example.pizzeria.entity;

import androidx.room.Entity;

@Entity
public class Ingredient extends BaseEntity {
    public String name;
    public int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
