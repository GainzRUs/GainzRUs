package com.marke.gainzrus;

import java.util.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Calendar time = Calendar.getInstance();
    TextView dateTextView = findViewById(R.id.date_text_view);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // hide app title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

    // textView is the TextView view that should display it
    dateTextView.

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

    public void onAddExerciseClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddExercise.class);
        startActivity(intent);
    }

}
