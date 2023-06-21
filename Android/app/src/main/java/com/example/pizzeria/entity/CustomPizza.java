package com.example.pizzeria.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = CASCADE
        )
})
public class CustomPizza extends Product{
    public long userId;
    public CustomPizza(String name, float price, int staticId, String ingredients, int pizza) {
        super(name, price, staticId, ingredients, pizza);
    }

    public CustomPizza(Product product, long userId){
        super(product.name, product.price, product.staticId, product.ingredients, product.pizza);
        this.userId = userId;
    }
}
