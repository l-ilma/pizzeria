package com.example.pizzeria.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pizzeria.entity.ProductOrder;

import java.util.List;

@Dao
public interface ProductOrderDao {
    @Insert
    public void insertAll(List<ProductOrder> productOrderList);

    @Transaction
    @Query("SELECT productId FROM productOrder WHERE orderId=:id")
    public List<Long> getProductsForOrder(long id);

    @Transaction
    @Query("SELECT * FROM ProductOrder")
    public List<ProductOrder> getAllProductOrderCrossRefs();
}
