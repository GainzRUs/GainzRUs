package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileSetup extends AppCompatActivity {

    User bodyBuilder = new User();
    private EditText userNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        getSupportActionBar().setTitle("Profile");

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

    public void onClickUserSettings(View view) {
        Intent intent = new Intent(ProfileSetup.this, Settings.class);
        startActivity(intent);
    }
}