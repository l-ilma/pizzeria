package com.example.pizzeria.ui.dialog;

public class Item {
    public Item(String name, long id) {
        this.name = name;
        selected = false;
        enabled = true;
        this.id = id;
    }
    public long id;
    public String name;
    public boolean selected;
    public boolean enabled;
}
