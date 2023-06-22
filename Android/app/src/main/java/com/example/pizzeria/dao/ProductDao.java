package com.example.pizzeria.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pizzeria.entity.Product;

import java.util.List;
@Dao
public interface ProductDao {
    @Insert
    public void insertAll(List<Product> products);

    @Transaction
    @Query("SELECT * from product")
    public List<Product> getAll();

    @Transaction
    @Query("SELECT * from product WHERE pizza = 1")
    public List<Product> getAllPizzas();

    @Transaction
    @Query("SELECT * from product WHERE pizza = 0")
    public List<Product> getAllDrinks();

    @Transaction
    @Query("SELECT * from product WHERE id=:id")
    public Product getProduct(long id);
}
