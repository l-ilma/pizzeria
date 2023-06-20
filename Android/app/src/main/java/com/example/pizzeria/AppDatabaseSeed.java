package com.example.pizzeria;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pizzeria.entity.Ingredient;
import com.example.pizzeria.entity.User;
import com.example.pizzeria.utils.Utilities;

import java.io.IOException;
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
            populateIngredients();
            populateProducts();
        });
    }

    public void populateProducts(){
        appDb.productDao().insertAll(Utilities.getProducts());
    }

    public void populateIngredients(){
        appDb.ingredientDao().insertAll(Arrays.asList(
                new Ingredient("Cheese", 18),
                new Ingredient("Pepperoni", 12),
                new Ingredient("Mushrooms", 15),
                new Ingredient("Onions", 14),
                new Ingredient("Bell peppers", 16),
                new Ingredient("Olives", 13),
                new Ingredient("Basil", 17),
                new Ingredient("Garlic", 10),
                new Ingredient("Oregano", 20),
                new Ingredient("Spinach", 11),
                new Ingredient("Sausage", 19),
                new Ingredient("Anchovies", 10),
                new Ingredient("Parmesan cheese", 12),
                new Ingredient("Ham", 15),
                new Ingredient("Pineapple", 16),
                new Ingredient("Jalapenos", 14),
                new Ingredient("Bacon", 18),
                new Ingredient("Artichokes", 13),
                new Ingredient("Feta cheese", 17),
                new Ingredient("Sun-dried tomatoes", 20),
                new Ingredient("Chicken", 11),
                new Ingredient("Ricotta cheese", 19),
                new Ingredient("Provolone cheese", 13),
                new Ingredient("Salami", 15),
                new Ingredient("Red onions", 17),
                new Ingredient("Green olives", 12),
                new Ingredient("Roasted garlic", 18),
                new Ingredient("Mozzarella cheese", 14),
                new Ingredient("Pesto sauce", 16),
                new Ingredient("Cherry tomatoes", 11),
                new Ingredient("Balsamic glaze", 20)
        ));
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
