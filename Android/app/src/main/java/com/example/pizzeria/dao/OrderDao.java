package com.example.pizzeria.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pizzeria.entity.Order;
import com.example.pizzeria.model.OrderWithProducts;
import com.example.pizzeria.utils.Status;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    public void insertAll(List<Order> orders);

    @Transaction
    @Query("SELECT * from `order` WHERE userId = :id")
    public List<Order> getOrdersForUser(long id);

    @Insert
    public long insertOne(Order order);

    @Transaction
    @Query("SELECT * from `order`")
    public LiveData<List<OrderWithProducts>> loadAllOrdersWithProducts();

    @Transaction
    @Query("SELECT * from `order`")
    public List<OrderWithProducts> loadAllOrdersWithProductsStatic();

    @Query("UPDATE `order` SET status = :status WHERE id = :id")
    public void updateStatus(long id, Status status);
}
