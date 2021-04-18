package com.example.the_cosmic_code;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class OutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DBProduct database;
    private Spaceship spaceship;
    private Button btBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        database = (DBProduct) getIntent().getSerializableExtra("DATABASE");
        spaceship = (Spaceship) getIntent().getSerializableExtra("SPACESHIP");
        ArrayList<Product> out;
        if (getIntent().getIntExtra("BEST_PRICE",0) > 500)
            out = database.selectAll(spaceship);
        else
            out = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView_out);
       ProductAdapter adapter = new ProductAdapter(this, out);
        recyclerView.setAdapter(adapter);
        btBackToMain = findViewById(R.id.bt_back_to_main);
        btBackToMain.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}