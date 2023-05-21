package com.example.pizzeria.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pizzeria.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuAdapter adapter = new MenuAdapter(this);
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adjustIconColor(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                adjustIconColor(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                if (position == 0) {
                    tab.setText(R.string.pizzas);
                    tab.setIcon(R.drawable.baseline_local_pizza_24);
                    adjustIconColor(tab, true);
                } else {
                    tab.setText(R.string.drinks);
                    tab.setIcon(R.drawable.baseline_local_drink_24);
                    adjustIconColor(tab, false);
                }
                }).attach();
    }
    private void adjustIconColor(TabLayout.Tab tab, boolean isSelected) {
        if(isSelected) {
            Objects.requireNonNull(tab.getIcon()).mutate().setTint(Color.parseColor("#ff5349"));
        } else {
            Objects.requireNonNull(tab.getIcon()).mutate().setTint(Color.parseColor("#9ca3af"));
        }
    }
}
