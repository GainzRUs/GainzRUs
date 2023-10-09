package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
public class AddExercise extends AppCompatActivity {

    private EditText editText;
    private Button submitButton;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);


        editText = findViewById(R.id.editText);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text entered by the user
                String userInput = editText.getText().toString();

                // Check if the input is not empty
                if (!userInput.isEmpty()) {
                    // Do something with the user's input (e.g., display it in a Toast)
                    Toast.makeText(AddExercise.this, "You entered: " + userInput, Toast.LENGTH_SHORT).show();
                } else {
                    // Display a message if the input is empty
                    Toast.makeText(AddExercise.this, "Please enter text.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity to go back to the previous activity (MainActivity)
                finish();
            }
        });


    }
}