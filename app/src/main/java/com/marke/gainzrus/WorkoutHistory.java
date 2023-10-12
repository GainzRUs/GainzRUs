package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

    // connect workoutList to listview in xml
    ListView workoutList = findViewById(R.id.list_view_workouts);

    // declare new workout array
    UserWorkout[] workouts =
            {
                    new UserWorkout("Bench", 10, 3, 135, "September"),
                    new UserWorkout("Military press", 12, 4, 95, "October")
            };

    // initialize array adapter
    ArrayAdapter<UserWorkout> workoutsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, workouts);
    workoutList.setAdapter(workoutsAdapter);

    }
}