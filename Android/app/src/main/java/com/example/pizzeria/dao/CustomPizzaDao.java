package com.example.pizzeria.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pizzeria.entity.CustomPizza;
import com.example.pizzeria.entity.Order;

import java.util.List;

@Dao
public interface CustomPizzaDao {
    @Insert
    public void insertAll(List<CustomPizza> pizzas);

    @Transaction
    @Query("SELECT * from `customPizza` WHERE userId = :id")
    public List<Order> getCustomPizzasForUser(long id);

    @Insert
    public long insertOne(CustomPizza customPizza);
}
