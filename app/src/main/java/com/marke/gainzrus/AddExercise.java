package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.view.View;


public class AddExercise extends AppCompatActivity {
    private LinearLayout setsContainer;
    private LinearLayout exerciseNamesLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

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

        updateInputFields(1);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Create a new exercise and populate it with user input
                Exercise exercise = createExerciseFromInput(exerciseNameEditText, setsNumberPicker, setsContainer);

                // Add the exercise to the ExerciseManager
                ExerciseManager.getInstance().addExercise(exercise);

                // Clear the input fields
                clearInputFields(exerciseNameEditText);

                // Update the UI by adding a TextView with the exercise name
                addExerciseNameToUI(exercise.getExerciseName());
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
            if (setView instanceof LinearLayout) {
                LinearLayout dynamicSetContainer = (LinearLayout) setView;

                if (dynamicSetContainer.getChildCount() == 2) {
                    EditText repsEditText = (EditText) dynamicSetContainer.getChildAt(0);
                    EditText weightEditText = (EditText) dynamicSetContainer.getChildAt(1);

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
            EditText repsEditText = new EditText(this);
            repsEditText.setHint("Reps for Set " + (i + 1));
            setsContainer.addView(repsEditText);

            EditText weightEditText = new EditText(this);
            weightEditText.setHint("Weight for Set " + (i + 1));
            setsContainer.addView(weightEditText);
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
}