package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pizzeria.menu.MenuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loadMenuFragment();
    }

    private void loadMenuFragment() {
        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), new MenuFragment())
                .commit();
    }



//    public void onGetBackendMessageClick(View view) {
//        TextView text = findViewById(R.id.backendTestText);
//
//        BackendHelloRepository.sayHello().enqueue(new Callback<BackendHello>() {
//            @Override
//            public void onResponse(@NonNull Call<BackendHello> call, @NonNull Response<BackendHello> response) {
//                assert response.body() != null;
//                text.setText(response.body().message);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<BackendHello> call, @NonNull Throwable t) {
//                text.setText(t.getMessage());
//            }
//        });
//    }
}