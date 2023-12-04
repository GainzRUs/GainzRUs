package com.marke.gainzrus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import android.widget.AdapterView;
import java.util.Objects;



public abstract class RandomWorkout extends AppCompatActivity {

    private final Map<String, String[]> workoutsByGroup = new HashMap<>();
    private TextView workoutTextView;
    private Spinner muscleGroupSpinner;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_workout);

        getSupportActionBar().setTitle("Random workout");

        initializeWorkouts();

        muscleGroupSpinner = findViewById(R.id.spinner_muscle_groups);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getMuscleGroups());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleGroupSpinner.setAdapter(adapter);

        workoutTextView = findViewById(R.id.text_workout);

        Button generateButton = findViewById(R.id.button_generate);
        generateButton.setOnClickListener(view -> {
            String selectedGroup = muscleGroupSpinner.getSelectedItem().toString();
            String workout = generateRandomWorkout(selectedGroup);
            workoutTextView.setText(workout);
        });

        Button openLinkButton = findViewById(R.id.button_open_link);
        openLinkButton.setOnClickListener(view -> openWebPage());
    }

    private void initializeWorkouts() {
        workoutsByGroup.put("Upper body", new String[]{
                "10 Push-ups",
                "5 Pull-ups",
                "25 Overhead presses",
                "15 ",
                "a",
                ""
                // Add more upper body workouts
        });
        workoutsByGroup.put("Lower body", new String[]{
                "15 Squats",
                "30 Lunges",
                "50 Jumping jacks",
                "a",
                "a",
                ""
                // Add more lower body workouts
        });
        workoutsByGroup.put("Core", new String[]{
                "20 Sit-ups",
                "30 Second plank",
                "50 Crunches",
                "20 Sit-ups",
                "20 High crunches",
                "20 Second elbow plank",
                "5 Back extensions",
                "20 Sitting twists",
                "20 Flutter kicks"
                // Add more core workouts
        });
        workoutsByGroup.put("Cardio", new String[]{
                "60 second running in place",
                "50 high knees",
                "30 burpees",
                "10 Lunge step-ups",
                "10 Sprinter lunges",
                ""

                // Add more cardio workouts
        });
        // Add other muscle groups if needed
    }

    private String[] getMuscleGroups() {
        return new String[] {"Upper body", "Lower body", "Core", "Cardio"};
    }

    private String generateRandomWorkout(String muscleGroup) {
        Random random = new Random();
        StringBuilder workoutsBuilder = new StringBuilder();
        String[] workouts = workoutsByGroup.get(muscleGroup);
        Set<String> chosenWorkouts = new HashSet<>();

        int workoutCount = 0;
        while (workoutCount < 5) {
            assert workouts != null;
            int index = random.nextInt(workouts.length);
            String workout = workouts[index];
            // Check if the workout has already been chosen
            if (!chosenWorkouts.contains(workout)) {
                chosenWorkouts.add(workout);
                workoutsBuilder.append(workout);
                workoutCount++;
                if (workoutCount < 5) {
                    workoutsBuilder.append("\n");
                }
            }
        }

        return workoutsBuilder.toString();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openWebPage() {
        Uri webpage = Uri.parse("https://www.youtube.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
