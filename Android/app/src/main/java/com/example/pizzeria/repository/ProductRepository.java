package com.example.pizzeria.repository;

import android.content.Context;

import com.example.pizzeria.AppDatabase;
import com.example.pizzeria.dao.ProductDao;
import com.example.pizzeria.entity.Product;

import java.util.List;

public class ProductRepository {
    final ProductDao productDao;
    public ProductRepository(Context context){
        productDao = AppDatabase.getInstance(context).productDao();
    }

    public List<Product> getAllPizzas(){
        return productDao.getAllPizzas();
    }

    public List<Product> getAllDrinks(){
        return productDao.getAllDrinks();
    }

    public Product getProduct(long id){
        return productDao.getProduct(id);
    }
}
