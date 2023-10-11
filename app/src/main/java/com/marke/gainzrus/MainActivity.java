package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide app title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

        // The click handler method defined in the XML layout
        public void onClickViewWorkout(View view) {
            Intent intent = new Intent(MainActivity.this, WorkoutHistory.class);
            startActivity(intent);
        }

        public void onClickUserProfile(View view)
        {
            Intent intent = new Intent(MainActivity.this, ProfileSetup.class);
            startActivity(intent);
        }
}
