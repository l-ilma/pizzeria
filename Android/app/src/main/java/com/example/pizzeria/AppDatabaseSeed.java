package com.example.pizzeria;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pizzeria.entity.Order;
import com.example.pizzeria.entity.ProductOrder;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.utils.Status;
import com.example.pizzeria.utils.Utilities;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class AppDatabaseSeed extends RoomDatabase.Callback {
    private final Context context;

    private AppDatabase appDb;

    public AppDatabaseSeed(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            appDb = AppDatabase.getInstance(context);

            populateUsers();
            populateProducts();
            populateOrders();
            populateCart();
        });
    }

    public void populateProducts() {
        appDb.productDao().insertAll(Utilities.getProducts());
    }

    public void populateUsers() {
        appDb.userDao().insertAll(
                Arrays.asList(
                        new User("ColinWilli", "colin.willi@gmail.com", "password", false, true),
                        new User("JacquelineMeyer", "jacqueline.meyer@gmail.com", "password", false, false),
                        new User("AnthonyMurray", "anthony.murray@gmail.com", "password", false, false),
                        new User("MichaelBurns", "michael.burns@gmail.com", "password", false, false),
                        new User("ThomasJohnson", "thomas.johnson", "password", false, false)
                ));
    }

    public void populateOrders() {
        appDb.orderDao().insertAll(
                Arrays.asList(
                        new Order(1, 16.55f, Status.ORDERED),
                        new Order(2, 18.55f, Status.ORDERED)
                )
        );
    }

    public void populateCart() {
        appDb.productOrderDao().insertAll(
                Arrays.asList(
                        new ProductOrder(1, 1),
                        new ProductOrder(1, 2),
                        new ProductOrder(1, 3),
                        new ProductOrder(1, 4),
                        new ProductOrder(1, 5),
                        new ProductOrder(1, 6)
                )
        );
    }
}
