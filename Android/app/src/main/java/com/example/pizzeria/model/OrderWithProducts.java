package com.example.pizzeria.model;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.entity.ProductOrder;

import java.util.List;

public class OrderWithProducts {
    @Embedded
    public Order order;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = ProductOrder.class,
                    parentColumn = "orderId",
                    entityColumn = "productId"
            )
    )
    public List<Product> products;
}
