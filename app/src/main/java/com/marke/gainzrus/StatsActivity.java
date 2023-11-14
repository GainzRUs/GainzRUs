package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import nl.dionsegijn.konfetti.xml.KonfettiView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().setTitle("Statistics");

        final KonfettiView konfettiView = findViewById(R.id.konfettiView);

        konfettiView.setOnClickListener((view) -> {
            konfettiView.buildLayer();/*.addColors(Color.BLUE, Color.GREEN, Color.BLACK)*/
        });
    }
}