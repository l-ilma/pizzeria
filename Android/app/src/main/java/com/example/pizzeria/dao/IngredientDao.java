package com.example.pizzeria.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pizzeria.entity.Ingredient;

import java.util.List;
@Dao
public interface IngredientDao {
    @Insert
    void insertAll(List<Ingredient> ingredients);

    @Transaction
    @Query("SELECT * from ingredient")
    List<Ingredient> getAllIngredients();

    @Transaction
    @Query("SELECT DISTINCT name from ingredient")
    List<String> getAllDistinctIngredientNames();

    @Insert
    long add(Ingredient ingredient);

    @Query("UPDATE Ingredient SET name = :name, quantity = :quantity AND id = :ingredientId")
    void update(long ingredientId, String name, int quantity);

    @Query("DELETE FROM Ingredient WHERE id = :id")
    void delete(long id);

    @Delete
    void removeAll(List<Ingredient> ingredients);

    @Update
    void updateAll(List<Ingredient> ingredients);
}
