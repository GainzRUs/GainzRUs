package com.marke.gainzrus;

import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

public class ProfileSetup extends AppCompatActivity {
    User bodyBuilder = new User();
    private EditText userNameText;
    private EditText userWeightText;
    private EditText userFeetText;
    private EditText userInchText;
    private TextView userBMIText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        getSupportActionBar().setTitle("Profile");

        // Find the Spinner view by its ID
        Spinner spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages 
        String[] pageNames = {"Profile", "Workout History", "Stats Activity","Add Exercise", "Workout Plan", "Settings", "Random workout"};

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
                        Intent option1Intent = new Intent(ProfileSetup.this, StatsActivity.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(ProfileSetup.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(ProfileSetup.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Workout Plan":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(ProfileSetup.this, WorkoutPlan.class);
                        startActivity(option4Intent);
                        break;
                    case "Settings":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(ProfileSetup.this, Settings.class);
                        startActivity(option5Intent);
                        break;
                    case "Random workout":
                        Intent option6Intent = new Intent(ProfileSetup.this, RandomWorkout.class);
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
    private void findViews()
    {
        userNameText = findViewById(R.id.userValue);
        userFeetText = findViewById(R.id.heightFeetValue);
        userInchText = findViewById(R.id.heightInchValue);
        userWeightText = findViewById(R.id.weightValue);
        userBMIText = findViewById(R.id.bmiValue);
    }

    // calculates and returns BMI
    private double findBMI(EditText feet, EditText inch, EditText w){
        // conversion in pounds
        double height, weight;
        // converting feet to inches; adding total inches
        height = Double.parseDouble(inch.getText().toString()) +
                (Double.parseDouble(feet.getText().toString()) * 12);
        // getting height
        weight = Double.parseDouble(w.getText().toString());
        // returning BMI
        return (weight/Math.pow(height,2))*703;

    }

    // sets user(bodyBuilder) values
    public void onClickAddUser(View view) {

        // calculate BMI
        double BMI = findBMI(userFeetText, userInchText, userWeightText);
        userBMIText.setText(String.format("%.1f",BMI));

        // setting body builder values
        bodyBuilder.setAll(userNameText.getText().toString(),
                String.valueOf(Double.parseDouble(userFeetText.getText().toString())*12 +
                        Double.parseDouble((userInchText.getText().toString()))),
                userWeightText.getText().toString(),
                userBMIText.getText().toString());


        // notification of profile saved
        makeText(this, "User " + bodyBuilder.getUserName() + " saved", LENGTH_SHORT).show();

        findViews();
    }

    public void onClickHomePage(View view){
        Intent intent = new Intent(ProfileSetup.this, MainActivity.class);
        startActivity(intent);
    }

} // end of class