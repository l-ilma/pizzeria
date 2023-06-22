package com.example.pizzeria.repository;

import android.content.Context;

import com.example.pizzeria.AppDatabase;
import com.example.pizzeria.dao.ProductOrderDao;
import com.example.pizzeria.entity.ProductOrder;

import java.util.List;

public class ProductOrderRepository {
    final ProductOrderDao productOrderDao;
    public ProductOrderRepository(Context context){
        productOrderDao = AppDatabase.getInstance(context).productOrderDao();
    }

    public void insertAll(List<ProductOrder> productOrders){
        productOrderDao.insertAll(productOrders);
    }

    public List<Long> getProductsForOrder(long orderId){
        return productOrderDao.getProductsForOrder(orderId);
    }
}
