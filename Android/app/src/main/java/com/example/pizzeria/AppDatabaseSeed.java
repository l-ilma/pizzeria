package com.example.pizzeria;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pizzeria.entity.User;
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
        });
    }

    public void populateProducts(){
        appDb.productDao().insertAll(Utilities.getProducts());
    }

    public void populateUsers(){
        appDb.userDao().insertAll(
                Arrays.asList(
                        new User("ColinWilli", "colin.willi@gmail.com", "password", false, true),
                        new User("JacquelineMeyer", "jacqueline.meyer@gmail.com", "password", false, false),
                        new User("AnthonyMurray", "anthony.murray@gmail.com", "password", false, false),
                        new User("MichaelBurns", "michael.burns@gmail.com", "password", false, false),
                        new User("ThomasJohnson", "thomas.johnson", "password", false, false)
                ));
    }
}
