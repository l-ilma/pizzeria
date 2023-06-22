package com.example.pizzeria.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Order.class,
                parentColumns = "id",
                childColumns = "orderId"
        )
})
public class CustomPizza extends Product {
    public long userId;

    public long orderId;

    public int quantity;

    public CustomPizza(String name, float price, int staticId, String ingredients, int pizza) {
        super(name, price, staticId, ingredients, pizza);
    }

    public CustomPizza(Product product, long userId, long orderId, int quantity) {
        super(product.name, product.price, product.staticId, product.ingredients, product.pizza);
        this.userId = userId;
        this.orderId = orderId;
        this.quantity = quantity;
    }
}
