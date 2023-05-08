package com.example.pizzeria.menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MenuAdapter extends FragmentStateAdapter {
    private static final int tabCount = 2;

    public MenuAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PizzaMenuFragment();
        } else {
            return new DrinksMenuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
