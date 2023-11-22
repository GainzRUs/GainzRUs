package com.marke.gainzrus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");
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
        alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {});

        // Create and show the AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}