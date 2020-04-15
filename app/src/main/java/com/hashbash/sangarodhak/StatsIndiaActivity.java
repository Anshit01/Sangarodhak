package com.hashbash.sangarodhak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hashbash.sangarodhak.Adapters.CountryDataRecyclerAdapter;

import java.util.ArrayList;

public class StatsIndiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_india);

        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> total = new ArrayList<>();
        ArrayList<String> active = new ArrayList<>();
        ArrayList<String> recovered = new ArrayList<>();
        ArrayList<String> dead = new ArrayList<>();

        codes.add("in");
        codes.add("in");

        name.add("India");
        name.add("India");

        total.add("34");
        total.add("23");

        active.add("23");
        active.add("21");

        recovered.add("2");
        recovered.add("1");

        dead.add("0");
        dead.add("0");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountryDataRecyclerAdapter(this, codes, name, total, active, recovered, dead));
    }
}
