package com.example.pizzeria;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.pizzeria.dao.CustomPizzaDao;
import com.example.pizzeria.dao.IngredientDao;
import com.example.pizzeria.dao.OrderDao;
import com.example.pizzeria.dao.ProductDao;
import com.example.pizzeria.dao.ProductOrderDao;
import com.example.pizzeria.dao.UserDao;
import com.example.pizzeria.entity.CustomPizza;
import com.example.pizzeria.entity.Ingredient;
import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.entity.ProductOrder;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.utils.Converters;

@Database(entities = {User.class, Product.class, Order.class, ProductOrder.class, Ingredient.class, CustomPizza.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "pizzeria.db";
    private static volatile AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context){
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .addCallback(new AppDatabaseSeed(context))
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }

    public abstract UserDao userDao();
    public abstract IngredientDao ingredientDao();
    public abstract OrderDao orderDao();
    public abstract ProductDao productDao();
    public abstract ProductOrderDao productOrderDao();
    public abstract CustomPizzaDao customPizzaDao();
}