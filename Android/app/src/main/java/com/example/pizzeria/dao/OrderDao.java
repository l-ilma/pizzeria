package com.example.pizzeria.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pizzeria.entity.Order;

import java.util.List;

public interface OrderDao {
    @Insert
    public void insertAll(List<Order> orders);

    @Transaction
    @Query("SELECT * from order WHERE userId = :id")
    public List<Order> getOrdersForUser(long id);
}
