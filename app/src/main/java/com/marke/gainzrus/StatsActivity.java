package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().setTitle("Statistics");
    }
}