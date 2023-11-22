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
    private EditText userFeetText;
    private EditText userInchText;
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
        userNameText = findViewById(R.id.userValue);
        userFeetText = findViewById(R.id.heightFeetValue);
        userInchText = findViewById(R.id.heightInchValue);
        userWeightText = findViewById(R.id.weightValue);
        userBMIText = findViewById(R.id.bmiValue);
        userBMIText.setText("0.0");
    }

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

    public void onClickAddUser(View view) {
        bodyBuilder.userName = userNameText.getText().toString();
        Toast.makeText(this, bodyBuilder.userName + " saved", Toast.LENGTH_SHORT).show();
        userNameText.getText().clear();

        // added BMI stuff
        double BMI = findBMI(userFeetText, userInchText, userWeightText);
        userBMIText.setText(String.format("%.1f",BMI));
    }

    public void onClickHomePage(View view){
        Intent intent = new Intent(ProfileSetup.this, MainActivity.class);
        startActivity(intent);
    }


}