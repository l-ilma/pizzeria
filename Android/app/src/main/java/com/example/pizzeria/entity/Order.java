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
public class Order extends BaseEntity{
    public long userId;
    public float price;

    public Order(long userId, float price) {
        this.userId = userId;
        this.price = price;
    }
}
