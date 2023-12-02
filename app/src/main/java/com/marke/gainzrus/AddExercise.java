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

import java.util.Date;


public class AddExercise extends AppCompatActivity {
    private LinearLayout setsContainer;

    private Spinner spinner_menu;
    private LinearLayout exerciseNamesLayout;
    private long currentWorkoutId;
    private Button finishWorkoutButton;
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
        Button finishWorkoutButton = findViewById(R.id.finishWorkoutButton);
        // Set the minimum and maximum values
        setsNumberPicker.setMinValue(1);
        setsNumberPicker.setMaxValue(10);

        // Set the default value
        setsNumberPicker.setValue(1);

        Spinner exerciseNameSpinner = findViewById(R.id.exerciseNameSpinner);
        Spinner exerciseRatingSpinner = findViewById(R.id.workoutRatingSpinner);
// Create an array of pre-selected exercise names
        String[] exerciseNames = {"Bench Press", "Squat", "Dead Lift", "Lateral Rows", "Shoulder Press"};
        String[] workoutRatings = {"✅", "👍", "🙂", "😕", "😡"};


// Set up the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseNameSpinner.setAdapter(adapter);

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workoutRatings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseRatingSpinner.setAdapter(ratingAdapter);

        setsNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateInputFields(newVal);
            }
        });

        // Find the Spinner view by its ID
        spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Add Exercise", "Workout History", "Stats Activity", "Profile", "Settings", "Workout plan"};

        // Initialize the Spinner with page names
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_menu.setAdapter(adapter2);

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
                    case "Settings":
                        Intent option4Intent = new Intent(AddExercise.this, Settings.class);
                        startActivity(option4Intent);
                        break;
                    case "Workout plan":
                        Intent option5Intent = new Intent(AddExercise.this, WorkoutPlan.class);
                        startActivity(option5Intent);
                        break;
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
                Exercise exercise = createExerciseFromInput(exerciseNameSpinner, exerciseRatingSpinner, setsNumberPicker, setsContainer);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddExercise.this);
                boolean b = dataBaseHelper.addExerciseWithSets(currentWorkoutId, exercise);
                Toast.makeText(AddExercise.this, "sucess" + b, Toast.LENGTH_SHORT).show();
                // Add the exercise to the ExerciseManager
                ExerciseManager.getInstance().addExercise(exercise);



                // Update the UI by adding a TextView with the exercise name
                addExerciseToUI(exercise);


            }
        });


        finishWorkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Finish the workout and insert the workout details into the database
                finishWorkout(exerciseRatingSpinner);
            }
        });

        currentWorkoutId = -1;

        startNewWorkout();

        LinearLayout exerciseNamesLayout = findViewById(R.id.exerciseNamesLayout); // Reference to the LinearLayout in your XML


    }

    private void startNewWorkout() {
        // Initialize the current workout ID and date
        currentWorkoutId = generateWorkoutId();
    }

    private void finishWorkout(Spinner exerciseRatingSpinner) {
        // Check if there are exercises added to the workout
        if (ExerciseManager.getInstance().getExerciseList().isEmpty()) {
            Toast.makeText(this, "No exercises added to the workout.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new workout and populate it with user input
        Workout workout = createWorkoutFromInput(exerciseRatingSpinner);

        // Insert the workout into the database
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddExercise.this);
        boolean success = dataBaseHelper.addWorkout(workout);

        if (success) {
            Toast.makeText(AddExercise.this, "Workout finished successfully", Toast.LENGTH_SHORT).show();

            // Clear the ExerciseManager
            ExerciseManager.getInstance().clearExerciseList();

            // Start a new workout
            startNewWorkout();
        } else {
            Toast.makeText(AddExercise.this, "Failed to finish the workout", Toast.LENGTH_SHORT).show();
        }
    }

    private int generateWorkoutId() {
        // Generate a unique workout ID (you can implement your logic for generating IDs)
        return (int) System.currentTimeMillis();
    }

    private Workout createWorkoutFromInput(Spinner workoutRatingSpinner) {
        // Create a new workout and populate it with user input
        Workout workout = new Workout();
        workout.setWorkoutId(currentWorkoutId);  // Set the workout ID
        String selectedRating = workoutRatingSpinner.getSelectedItem().toString();
        workout.setWorkoutRating(selectedRating);
        workout.setWorkoutDate(new Date());  // Set the current date as the workout date

        return workout;
    }
    private Exercise createExerciseFromInput(Spinner exerciseNameSpinner, Spinner exerciseRatingSpinner, NumberPicker setsNumberPicker, LinearLayout setsContainer) {
        String exerciseName = exerciseNameSpinner.getSelectedItem().toString();
        String exerciseRating = exerciseRatingSpinner.getSelectedItem().toString();

        int sets = setsNumberPicker.getValue();

        // Create an Exercise object and populate it
        Exercise exercise = new Exercise(exerciseName);

        // Populate sets' details (reps, weight) as needed
        for (int i = 0; i < sets; i++) {
            // Retrieve reps and weight from your dynamic input fields
            // Add a new ExerciseSet to the exercise with the retrieved values
            // For example:
            View setView = setsContainer.getChildAt(i);

            if (setView instanceof LinearLayout) {
                LinearLayout dynamicSetContainer = (LinearLayout) setView;

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

    public void onClickHomePage(View view) {
        Intent intent = new Intent(AddExercise.this, MainActivity.class);
        startActivity(intent);
    }
}