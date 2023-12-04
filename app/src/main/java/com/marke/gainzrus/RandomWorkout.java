package com.marke.gainzrus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText editTextNumberOfWorkouts;

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

        editTextNumberOfWorkouts = findViewById(R.id.editText_numberOfWorkouts);

        workoutTextView = findViewById(R.id.text_workout);

        Button generateButton = findViewById(R.id.button_generate);
        generateButton.setOnClickListener(view -> {
            String selectedGroup = muscleGroupSpinner.getSelectedItem().toString();
            int numberOfWorkouts = parseWorkoutCount();
            String workout = generateRandomWorkout(selectedGroup, numberOfWorkouts);
            workoutTextView.setText(workout);
        });


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
                "25 Push-ups",
                "10 Pull-ups",
                "25 Overhead presses",
                "12 Dumbbell bench presses",
                "15 Dumbbell curls",
                "15 Shoulder presses",
                "20 Tricep kickbacks",
                "12 Lat pulldowns",
                "15 Dumbbell overhead shoulder press",
                "10 Shrugs"
                // Add more upper body workouts
        });
        workoutsByGroup.put("Lower body", new String[]{
                "25 Squats",
                "30 Lunges",
                "50 Jumping jacks",
                "30 Donkey kicks",
                "30 Glute bridges",
                "20 Reverse lunge front kicks",
                "10 Bulgarian split squats",
                "15 Leg extensions",
                "15 Jump squats",
                "15 Leg presses"
                // Add more lower body workouts
        });
        workoutsByGroup.put("Core", new String[]{
                "2x20 Sit-ups",
                "30-Second Plank",
                "50 Crunches",
                "25 Leg lifts",
                "20 High crunches",
                "20 Second elbow plank",
                "5 Back extensions",
                "20 Sitting twists",
                "20 Flutter kicks",
                "25 Knee-to-elbow crunches"
                // Add more core workouts
        });
        workoutsByGroup.put("Cardio", new String[]{
                "60 second running in place",
                "50 high knees",
                "30 burpees",
                "10 Lunge step-ups",
                "10 Sprinter lunges",
                "20 Lunge step-ups",
                "1 Minute mountain climber",
                "50 Jumping jacks",
                "30 Jump squats",
                "1 Minute jump roping"
                // Add more cardio workouts
        });

        workoutsByGroup.put("Stretching", new String[]{
                "Neck stretch",
                "Shoulder stretch",
                "Arm and wrist stretch",
                "Chest stretch",
                "Side stretch",
                "Hamstring stretch",
                "Quad stretch",
                "Calf stretch",
                "Ankle stretch",
                // Add more stretching workouts
        });

        workoutsByGroup.put("Yoga", new String[]{
                "Downward Dog",
                "Warrior I",
                "Warrior II",
                "Tree Pose",
                "Child's Pose",
                "Cobra Pose",
                "Cat-Cow Stretch",
                "Bridge Pose",
                "Seated Forward Bend",
                // Add more yoga poses
        });
    }

    private String[] getMuscleGroups() {
        return new String[]{"Upper body", "Lower body", "Core", "Cardio", "Stretching", "Yoga"};
    }

    private int parseWorkoutCount() {
        try {
            return Integer.parseInt(editTextNumberOfWorkouts.getText().toString());
        } catch (NumberFormatException e) {
            return 5; // Default number if input is invalid
        }
    }

    private String generateRandomWorkout(String muscleGroup, int numberOfWorkouts) {
        Random random = new Random();
        StringBuilder workoutsBuilder = new StringBuilder();
        String[] workouts = workoutsByGroup.get(muscleGroup);
        Set<String> chosenWorkouts = new HashSet<>();

        int workoutCount = 0;
        assert workouts != null;
        numberOfWorkouts = Math.min(numberOfWorkouts, workouts.length); // Ensure we don't exceed array length
        while (workoutCount < numberOfWorkouts) {
            int index = random.nextInt(workouts.length);
            String workout = workouts[index];
            if (!chosenWorkouts.contains(workout)) {
                chosenWorkouts.add(workout);
                workoutsBuilder.append(workout);
                workoutCount++;
                if (workoutCount < numberOfWorkouts) {
                    workoutsBuilder.append("\n");
                }
            }
        }

        return workoutsBuilder.toString();
    }

    public void onClickHomePage(View view) {
        Intent intent = new Intent(RandomWorkout.this, MainActivity.class);
        startActivity(intent);
    }
}
