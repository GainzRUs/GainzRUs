package com.marke.gainzrus;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
                "20 Sit-ups",
                "30 Second plank",
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

}
