package com.example.pizzeria.ui.authentication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pizzeria.R;

public class AuthenticationMenuAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 2;

    public AuthenticationMenuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0
                ? new Fragment(R.layout.login_fragment)
                : new Fragment(R.layout.register_fragment);
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
