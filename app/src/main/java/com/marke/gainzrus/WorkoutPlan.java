package com.marke.gainzrus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutPlan extends AppCompatActivity {

    private TextView textViewSelectedPlan;
    private LinearLayout plansContainer;
    private Map<String, List<List<String>>> workoutPlans;

    private EditText editTextWorkLength;
    public int workLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_plan_selection_advanced);

        getSupportActionBar().setTitle("Workout Plans");

        editTextWorkLength = findViewById(R.id.editTextWorkLength);


        // Initialize views
        RadioGroup radioGroupLevels = findViewById(R.id.radioGroupLevels);
        Button buttonSelectPlan = findViewById(R.id.buttonSelectPlan);
        textViewSelectedPlan = findViewById(R.id.textViewSelectedPlan);
        plansContainer = findViewById(R.id.plansContainer);

        initializeWorkoutPlans();

        buttonSelectPlan.setOnClickListener(view -> {

            String workLengthText = editTextWorkLength.getText().toString().trim();
            if (!workLengthText.isEmpty()) {
                workLength = Integer.parseInt(workLengthText);

            } else {
                editTextWorkLength.setError("Please enter work length.");
            }

            int checkedRadioButtonId = radioGroupLevels.getCheckedRadioButtonId();
            String selectedPlan = getSelectedPlan(checkedRadioButtonId);
            textViewSelectedPlan.setTextColor(Color.BLUE);
            textViewSelectedPlan.setText("Selected Workout Plan: " + selectedPlan);

            // Clear previous plans
            plansContainer.removeAllViews();

            if (workoutPlans.containsKey(selectedPlan)) {
                List<List<String>> plans = workoutPlans.get(selectedPlan);

                // Display three plans for the selected intensity level
                for (List<String> workoutPlan : plans) {
                    LinearLayout rowLayout = new LinearLayout(this);
                    rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    rowLayout.setOrientation(LinearLayout.VERTICAL);

                    int count = 0;
                    for (String workout : workoutPlan) {
                        if (count < workLength) {
                            TextView textView = new TextView(this);
                            textView.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            textView.setText(workout);
                            textView.setPadding(16, 8, 16, 8);
                            textView.setTypeface(null, Typeface.BOLD);
                            rowLayout.addView(textView);
                            count++;
                        } else {
                            break; // Break the loop after displaying three workouts
                        }

                    }
                    rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    plansContainer.addView(rowLayout);
                }
            }
        });
        Spinner spinner_menu = findViewById(R.id.spinner_menu);

        String[] pageNames = {"Workout Plan", "Workout History", "Stats Activity", "Add Exercise", "Profile", "Settings"};

        // Find the Spinner view by its ID
        spinner_menu = findViewById(R.id.spinner_menu);

        // Initialize the Spinner with page names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_menu.setAdapter(adapter);

        // Set listener for Spinner item selection
        spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Perform actions based on the selected item
                switch (selectedItem) {
                    case "Stats Activity":
                        // Navigate to Activity related to Option 1
                        Intent option1Intent = new Intent(WorkoutPlan.this, StatsActivity.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(WorkoutPlan.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(WorkoutPlan.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Profile":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(WorkoutPlan.this, ProfileSetup.class);
                        startActivity(option4Intent);
                        break;
                    case "Settings":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(WorkoutPlan.this, Settings.class);
                        startActivity(option5Intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeWorkoutPlans() {
        workoutPlans = new HashMap<>();

        List<List<String>> beginnerWorkouts = new ArrayList<>();
        beginnerWorkouts.add(new ArrayList<String>() {{
            add("Cardio 30mins");
            add("Planks 3x30secs");
            add("Push-ups 3x10");
            add("Jump Rope 15mins");
            add("Russian Twists 3x20");
            add("Russian Twists 3x20");
            add("Pull-ups 3x8");
            add("Bicep Curls 3x12");
            add("Walking Lunges 3x15");
            add("Cycling 45mins");
        }});
        workoutPlans.put("Beginner", beginnerWorkouts);

        // Add intermediate
        List<List<String>> intermediateWorkouts = new ArrayList<>();

        intermediateWorkouts.add(new ArrayList<String>() {{
            add("Squat Jumps 5x15");
            add("Deadlifts 4x8");
            add("Burpees 3x15");
            add("Lunges 3x12");
            add("Circuit Training");
            add("Box Jumps 3x10");
            add("Kettlebell Swings 4x15");
            add("Battle Rope Exercises 3x20");
            add("Sprints 5x100m");
            add("Leg Press 4x12");
        }});
        workoutPlans.put("Intermediate", intermediateWorkouts);

// Advanced Workouts
        List<List<String>> advancedWorkouts = new ArrayList<>();
        advancedWorkouts.add(new ArrayList<String>() {{
            add("Overhead Press 5x5");
            add("Power Cleans 5x5");
            add("Muscle-ups 4x8");
            add("Plyometric Training");
            add("Barbell Squats 4x10");
            add("Hill Sprints 10x100m");
            add("Handstand Push-ups 4x12");
            add("Pistol Squats 3x8");
            add("Ring Dips 4x10");
            add("Snatch Exercises 5x5");
        }});
        workoutPlans.put("Advanced", advancedWorkouts);



    }

    private String getSelectedPlan(int checkedRadioButtonId) {
        RadioButton radioButton = findViewById(checkedRadioButtonId);
        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
    }

    public void onClickHomePage(View view) {
        Intent intent = new Intent(WorkoutPlan.this, MainActivity.class);
        startActivity(intent);
    }
}
