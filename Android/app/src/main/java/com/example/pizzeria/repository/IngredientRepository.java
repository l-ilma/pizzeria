package com.example.pizzeria.repository;

import android.content.Context;

import com.example.pizzeria.AppDatabase;
import com.example.pizzeria.dao.IngredientDao;
import com.example.pizzeria.entity.Ingredient;
import com.example.pizzeria.entity.Product;

import java.util.List;

public class IngredientRepository {
    final IngredientDao ingredientsDao;

    public IngredientRepository(Context context) {
        ingredientsDao = AppDatabase.getInstance(context).ingredientDao();
    }

    public List<Ingredient> getAll(){
        return ingredientsDao.getAllIngredients();
    }
}
