package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.NumberPicker;


public class AddExercise extends AppCompatActivity {
    private LinearLayout setsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        setsContainer = findViewById(R.id.setsContainer);
        // Find the NumberPicker view by its ID
        NumberPicker setsNumberPicker = findViewById(R.id.setsNumberPicker);

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
}