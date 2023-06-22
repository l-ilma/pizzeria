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
    public String address;
    public String zip;
    public String note;


    public Order(long userId, float price, Status status, String address, String zip, String note) {
        this.userId = userId;
        this.price = price;
        this.status = status;
        this.address = address;
        this.zip = zip;
        this.note = note;
    }
}
