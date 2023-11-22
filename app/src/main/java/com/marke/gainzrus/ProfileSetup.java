package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileSetup extends AppCompatActivity {

    private Spinner spinner_menu;

    User bodyBuilder = new User();
    private EditText userNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        getSupportActionBar().setTitle("Profile");

        // Find the Spinner view by its ID
        spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Profile", "Workout History", "Stats Activity","Add Exercise"};

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
                        Intent option1Intent = new Intent(ProfileSetup.this, MainActivity.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(ProfileSetup.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 1
                        Intent option3Intent = new Intent(ProfileSetup.this, AddExercise.class);
                        startActivity(option3Intent);
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

    private void findViews()
    {
        userNameText = findViewById(R.id.editText_user_name);
    }

    public void onClickAddUser(View view) {
        bodyBuilder.userName = userNameText.getText().toString();
        Toast.makeText(this, bodyBuilder.userName + " saved", Toast.LENGTH_SHORT).show();
        userNameText.getText().clear();
//        Intent intent = new Intent(ProfileSetup.this, WorkoutHistory.class);
//        startActivity(intent);
    }
}