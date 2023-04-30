package com.example.pizzeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pizzeria.domain.BackendHello;
import com.example.pizzeria.repository.BackendHelloRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetBackendMessageClick(View view) {
        TextView text = findViewById(R.id.backendTestText);

        BackendHelloRepository.sayHello().enqueue(new Callback<BackendHello>() {
            @Override
            public void onResponse(@NonNull Call<BackendHello> call, @NonNull Response<BackendHello> response) {
                assert response.body() != null;
                text.setText(response.body().message);
            }

            @Override
            public void onFailure(@NonNull Call<BackendHello> call, @NonNull Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }
}