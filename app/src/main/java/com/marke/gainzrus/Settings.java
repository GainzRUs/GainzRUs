package com.marke.gainzrus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private EditText userNameText;
    private EditText userWeightText;
    private EditText userFeetText;
    private EditText userInchText;
    private TextView userBMIText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");

        // Find the Spinner view by its ID
        Spinner spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Settings", "Stats activity", "Profile", "Workout History", "Add Exercise", "Workout plan", "Random workout"};

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
                    case "Profile":
                        // Navigate to Activity related to Option 1
                        Intent option1Intent = new Intent(Settings.this, ProfileSetup.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(Settings.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(Settings.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Stats activity":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(Settings.this, StatsActivity.class);
                        startActivity(option4Intent);
                        break;
                    case "Workout plan":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(Settings.this, WorkoutPlan.class);
                        startActivity(option5Intent);
                        break;
                    case "Random workout":
                        Intent option6Intent = new Intent(Settings.this, RandomWorkout.class);
                        startActivity(option6Intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle scenario when nothing is selected (if needed)
            }
        });

        findViews();
    }

    // searches for/initializes values on profile screen
    private void findViews() {
        userNameText = findViewById(R.id.userValue);
        userFeetText = findViewById(R.id.heightFeetValue);
        userInchText = findViewById(R.id.heightInchValue);
        userWeightText = findViewById(R.id.weightValue);
        userBMIText = findViewById(R.id.bmiValue);
    }

    public void onClickClearData(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to clear all data?");
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // User confirmed, perform the data clearing action here
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            dbHelper.clearDatabase();
            Toast.makeText(this, "All data cleared", Toast.LENGTH_SHORT).show();
        });
        alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onClickHomePage(View view) {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }
}