package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.view.View;
import android.widget.Toast;


public class AddExercise extends AppCompatActivity {
    private LinearLayout setsContainer;

    private Spinner spinner_menu;
    private LinearLayout exerciseNamesLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        getSupportActionBar().setTitle("Add exercise");

        setsContainer = findViewById(R.id.setsContainer);
        // Find the NumberPicker view by its ID
        exerciseNamesLayout = findViewById(R.id.exerciseNamesLayout);
        NumberPicker setsNumberPicker = findViewById(R.id.setsNumberPicker);
        Button addExerciseButton = findViewById(R.id.addExerciseButton);
        EditText exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        // Set the minimum and maximum values
        setsNumberPicker.setMinValue(1);
        setsNumberPicker.setMaxValue(10);

        // Set the default value
        setsNumberPicker.setValue(1);

        setsNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateInputFields(newVal);
            }
        });

        // Find the Spinner view by its ID
        spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Add Exercise", "Workout History", "Stats Activity", "Profile"};

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
                        Intent option1Intent = new Intent(AddExercise.this, StatsActivity.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        Intent option2Intent = new Intent(AddExercise.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Profile":
                        Intent option3Intent = new Intent(AddExercise.this, ProfileSetup.class);
                        startActivity(option3Intent);
                        break;
                    // Add cases for other items if needed
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });

        updateInputFields(1);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Create a new exercise and populate it with user input
                Exercise exercise = createExerciseFromInput(exerciseNameEditText, setsNumberPicker, setsContainer);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddExercise.this);

                boolean b = dataBaseHelper.addExerciseWithSets(exercise);
                Toast.makeText(AddExercise.this, "sucess" + b, Toast.LENGTH_SHORT).show();
                // Add the exercise to the ExerciseManager
                ExerciseManager.getInstance().addExercise(exercise);

                // Clear the input fields
                clearInputFields(exerciseNameEditText);

                // Update the UI by adding a TextView with the exercise name
                addExerciseToUI(exercise);


            }
        });


        LinearLayout exerciseNamesLayout = findViewById(R.id.exerciseNamesLayout); // Reference to the LinearLayout in your XML


    }
    private Exercise createExerciseFromInput(EditText exerciseNameEditText, NumberPicker setsNumberPicker, LinearLayout setsContainer) {
        String exerciseName = exerciseNameEditText.getText().toString();
        int sets = setsNumberPicker.getValue();

        // Create an Exercise object and populate it
        Exercise exercise = new Exercise(exerciseName);

        // Populate sets' details (reps, weight) as needed
        for (int i = 0; i < sets; i++) {
            // Retrieve reps and weight from your dynamic input fields
            // Add a new ExerciseSet to the exercise with the retrieved values
            // For example:
            View setView = setsContainer.getChildAt(i);
            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show(); // we make it here
            if (setView instanceof LinearLayout) {
                LinearLayout dynamicSetContainer = (LinearLayout) setView;
                Toast.makeText(this, "here", Toast.LENGTH_SHORT).show(); // we do not make it here
                if (dynamicSetContainer.getChildCount() == 2) {
                    EditText repsEditText = (EditText) dynamicSetContainer.getChildAt(0);
                    EditText weightEditText = (EditText) dynamicSetContainer.getChildAt(1);

                    Toast.makeText(this, "Reps: " + repsEditText.getText().toString() + ", Weight: " + weightEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    int reps = Integer.parseInt(repsEditText.getText().toString());
                    double weight = Double.parseDouble(weightEditText.getText().toString());

                    ExerciseSet exerciseSet = new ExerciseSet(reps, weight);
                    exercise.addExerciseSet(exerciseSet);
                }
            }
        }

        return exercise;
    }

    private void updateInputFields(int numOfSets) {
        // Remove any existing EditText fields
        setsContainer.removeAllViews();

        for (int i = 0; i < numOfSets; i++) {
            LinearLayout dynamicSetContainer = new LinearLayout(this);
            dynamicSetContainer.setOrientation(LinearLayout.HORIZONTAL);

            EditText repsEditText = new EditText(this);
            repsEditText.setHint("Reps for Set " + (i + 1));
            dynamicSetContainer.addView(repsEditText);

            EditText weightEditText = new EditText(this);
            weightEditText.setHint("Weight for Set " + (i + 1));
            dynamicSetContainer.addView(weightEditText);

            setsContainer.addView(dynamicSetContainer);
        }
    }
    private void clearInputFields(EditText exerciseNameEditText) {
        exerciseNameEditText.setText("");
        // Clear other input fields as needed
    }
    private void updateExerciseNamesLayout(LinearLayout exerciseNamesLayout) {
        for (Exercise exercise : ExerciseManager.getInstance().getExerciseList()) {
            // Create a TextView for the exercise name
            TextView exerciseNameTextView = new TextView(this);
            exerciseNameTextView.setText(exercise.getExerciseName());

            // Set any additional attributes for the TextView, e.g., text size, padding, etc.

            // Add the TextView to the exerciseNamesLayout
            exerciseNamesLayout.addView(exerciseNameTextView);
        }
    }

    private void addExerciseNameToUI(String exerciseName) {
        TextView exerciseNameTextView = new TextView(this);
        exerciseNameTextView.setText(exerciseName);
        exerciseNamesLayout.addView(exerciseNameTextView);
    }
    private void addExerciseToUI(Exercise exercise) {
        // Create a LinearLayout to hold all exercise information
        LinearLayout exerciseLayout = new LinearLayout(this);
        exerciseLayout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the exercise name
        TextView exerciseNameTextView = new TextView(this);
        exerciseNameTextView.setText("Exercise Name: " + exercise.getExerciseName());
        exerciseLayout.addView(exerciseNameTextView);

        // Iterate through exercise sets and display information
        for (ExerciseSet set : exercise.getExerciseSets()) {
            TextView setTextView = new TextView(this);
            setTextView.setText("Set - Reps: " + set.getNumberOfReps() + ", Weight: " + set.getWeight());
            exerciseLayout.addView(setTextView);
        }

        // Add the exercise layout to the main layout
        exerciseNamesLayout.addView(exerciseLayout);
    }
}