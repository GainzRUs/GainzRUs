package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide app title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    // The click handler method defined in the XML layout
    public void onAddExerciseClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddExercise.class);
        startActivity(intent);
    }
}