package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileSetup extends AppCompatActivity {

    User bodyBuilder = new User();
    private EditText userNameText;
    private EditText userWeightText;
    private EditText userHeightText;
    private TextView userBMIText;
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
        userHeightText = findViewById(R.id.editText_height);
        userWeightText = findViewById(R.id.editText_weight);
        userBMIText = findViewById(R.id.bmiValue);
        userBMIText.setText("0.0");
    }

    private double findBMI(EditText h, EditText w){
        // conversion in pounds
        double height = 0.0, weight = 0.0;
        height = Double.parseDouble(h.getText().toString());
        weight = Double.parseDouble(w.getText().toString());
        return (weight/Math.pow(height,2))*703;
    }

    public void onClickAddUser(View view) {
        bodyBuilder.userName = userNameText.getText().toString();
        Toast.makeText(this, bodyBuilder.userName + " saved", Toast.LENGTH_SHORT).show();
        userNameText.getText().clear();

        // added BMI stuff
        double BMI = findBMI(userHeightText,userWeightText);
        userBMIText.setText(String.valueOf(BMI));
    }

    public void onClickHomePage(View view){
        Intent intent = new Intent(ProfileSetup.this, MainActivity.class);
        startActivity(intent);
    }
}