package com.example.pizzeria.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.pizzeria.utils.Status;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = CASCADE
        )
})
public class Order extends BaseEntity {
    public long userId;
    public float price;
    public Status status;


    public Order(long userId, float price, Status status) {
        this.userId = userId;
        this.price = price;
        this.status = status;
    }
}
