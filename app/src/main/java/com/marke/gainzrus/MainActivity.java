package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide app title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    // The click handler method defined in the XML layout
    public void onButtonClick(View view) {
        // Your code to handle the button click event
        // For example, you can display a Toast message
        Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();
    }
}