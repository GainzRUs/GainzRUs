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

public class RandomWorkout extends AppCompatActivity {

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

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Random Workout", "Profile", "Workout History", "Settings", "Add Exercise", "Workout plan", "Stats activity"};

        Spinner spinner_menu = findViewById(R.id.spinner_menu);
        // Initialize the Spinner with page names
        ArrayAdapter<String> nav_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pageNames);
        nav_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_menu.setAdapter(nav_adapter);

        // Set listener for Spinner item selection
        spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Perform actions based on the selected item
                switch (selectedItem) {
                    case "Profile":
                        // Navigate to Activity related to Option 1
                        Intent option1Intent = new Intent(RandomWorkout.this, ProfileSetup.class);
                        startActivity(option1Intent);
                        break;
                    case "Stats activity":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(RandomWorkout.this, StatsActivity.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(RandomWorkout.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Settings":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(RandomWorkout.this, Settings.class);
                        startActivity(option4Intent);
                        break;
                    case "Workout plan":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(RandomWorkout.this, WorkoutPlan.class);
                        startActivity(option5Intent);
                        break;
                    case "Workout History":
                        Intent option6Intent = new Intent(RandomWorkout.this, WorkoutHistory.class);
                        startActivity(option6Intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle scenario when nothing is selected (if needed)
            }
        });
    }

    private void initializeWorkouts() {
        workoutsByGroup.put("Upper body", new String[]{
                "3x10 Push-ups",
                "4x5 Pull-ups",
                "2x25 Overhead Press",
                "3x10 Dips",
                "3x12 Bicep Curls",
                "4x8 Chest Fly"
                // Add more upper body workouts
        });
        workoutsByGroup.put("Lower body", new String[]{
                "3x15 Squats",
                "2x30 Lunges",
                "50 Jumping Jacks",
                "3x15 Leg Extensions",
                "3x12 Hamstring Curls",
                "3x10 Calf Raises"
                // Add more lower body workouts
        });
        workoutsByGroup.put("Core", new String[]{
                "2x20 Sit-ups",
                "30-Second Plank",
                "50 Crunches",
                "3x20 Sit-ups",
                "3x20 High Crunches",
                "20-Second Elbow Plank",
                "3x5 Back Extensions",
                "2x20 Sitting Twists",
                "2x20 Flutter Kicks"
                // Add more core workouts
        });
        workoutsByGroup.put("Cardio", new String[]{
                "60-Second Running in Place",
                "50 High Knees",
                "30 Burpees",
                "2x10 Lunge Step-ups",
                "2x10 Sprinter Lunges",
                "30-Minute Inclined Walk"

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

    public void onClickHomePage(View view) {
        Intent intent = new Intent(RandomWorkout.this, MainActivity.class);
        startActivity(intent);
    }
}
