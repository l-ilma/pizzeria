package com.example.pizzeria.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"orderId", "productId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Order.class,
                        parentColumns = "id",
                        childColumns = "orderId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = CASCADE
                )
        })
public class ProductOrder {
    public long orderId;
    public long productId;

    public ProductOrder(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
