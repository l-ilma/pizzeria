package com.example.pizzeria.repository;

import android.content.Context;

import com.example.pizzeria.AppDatabase;
import com.example.pizzeria.dao.CustomPizzaDao;
import com.example.pizzeria.dao.OrderDao;
import com.example.pizzeria.entity.CustomPizza;
import com.example.pizzeria.entity.Order;

public class CustomPizzaRepository {
    final CustomPizzaDao customPizzaDao;
    public CustomPizzaRepository(Context context){
        customPizzaDao = AppDatabase.getInstance(context).customPizzaDao();
    }

    public long insertOne(CustomPizza customPizza){
        return customPizzaDao.insertOne(customPizza);
    }
}
