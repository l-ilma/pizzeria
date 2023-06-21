package com.example.pizzeria.repository;

import android.content.Context;

import com.example.pizzeria.AppDatabase;
import com.example.pizzeria.dao.OrderDao;
import com.example.pizzeria.entity.Order;

public class OrderRepository {
    final OrderDao orderDao;
    public OrderRepository(Context context){
        orderDao = AppDatabase.getInstance(context).orderDao();
    }

    public long insertOne(Order order){
        return orderDao.insertOne(order);
    }
}
