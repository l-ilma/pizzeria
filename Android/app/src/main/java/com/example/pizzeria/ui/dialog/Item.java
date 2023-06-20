package com.example.pizzeria.ui.dialog;

public class Item {
    public Item(String name) {
        this.name = name;
        selected = false;
        enabled = true;
    }
    public String name;
    public boolean selected;
    public boolean enabled;
}
