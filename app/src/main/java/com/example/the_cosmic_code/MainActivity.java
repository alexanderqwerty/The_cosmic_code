package com.example.the_cosmic_code;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SpaceshipAdapter.AdapterListener, SpaceshipAdapter.OnDeleteSpaceshipListener {
    private ArrayList<Spaceship> spaceships;
    private DBProduct database;
    private ImageButton bt;
    private RecyclerView recyclerView;
    private SpaceshipAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.bt_add_spaceship);
        database = new DBProduct(this);
        spaceships = new ArrayList<>();
        spaceships = database.selectAllSpaceShip();
        recyclerView = findViewById(R.id.list_spaceships);
        adapter = new SpaceshipAdapter(this, spaceships);
        recyclerView.setAdapter(adapter);


        bt.setOnClickListener(v -> {
            spaceships.add(new Spaceship());
            adapter.setSpaceships(spaceships);
            recyclerView.setAdapter(adapter);
        });
    }


    @Override
    public void onClickAdapter(int position) {
        Intent i = new Intent(this, AddProductActivity.class);
        i.putExtra("SHIP", spaceships.get(position));
        i.putExtra("DATABASE", database);
        startActivity(i);
    }

    @Override
    public void onDeleteSpaceshipEl(int pos) {
        spaceships.remove(spaceships.get(pos));
       SpaceshipAdapter adapter = new SpaceshipAdapter(this, spaceships);
        recyclerView.setAdapter(adapter);
    }
}
