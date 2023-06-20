package com.example.pizzeria.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public Date createdAt;

    public BaseEntity() {
        createdAt = new Date();
    }
}
