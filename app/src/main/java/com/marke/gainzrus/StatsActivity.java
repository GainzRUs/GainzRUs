package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;

import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class StatsActivity extends AppCompatActivity {

    private EditText userNameText;
    private EditText userWeightText;
    private EditText userFeetText;
    private EditText userInchText;
    private TextView userBMIText;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // change title of page
        getSupportActionBar().setTitle("Statistics");

        // bind textviews for each exercise
        TextView benchText = findViewById(R.id.benchValue_text_view);
        TextView squatText = findViewById(R.id.squatValue_text_view);
        TextView deadliftText = findViewById(R.id.deadliftValue_text_view);

        // bind button for confetti blast
        Button button;
        button = findViewById(R.id.konfetti_button);

        // bind confetti view and define its blast attributes
        KonfettiView konfettiView = findViewById(R.id.konfettiView);

        Shape.DrawableShape drawableShape = new Shape.DrawableShape(AppCompatResources.getDrawable(this, R.drawable.ic_confetti1), true);

        button.setOnClickListener(view -> {
            EmitterConfig emitterConfig = new Emitter(300, TimeUnit.MILLISECONDS).max(300);
            konfettiView.start(
                    new PartyFactory(emitterConfig)
                            .shapes(Shape.Circle.INSTANCE, Shape.Square.INSTANCE, drawableShape)
                            .spread(360)
                            .position(0.5, 0.5, 0.5, 0.5)
                            .sizes(new Size(8, 50, 10))
                            .timeToLive(1000)
                            .fadeOutEnabled(true)
                            .build()
            );
        });

        // variable declarations for 3 PRs
        double benchMax = 0.00, squatMax = 0.00, deadliftMax = 0.00;
        int benchReps = 0, squatReps = 0, deadliftReps = 0;

        // Fetch exercise data from the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        List<Exercise> allExercises = dbHelper.getAllExercises();

        // go through exercises in database
        for (Exercise exercise : allExercises) {
            String exerciseName = exercise.getExerciseName().trim();

            // if exercise isn't one of these go to next exercise in database
            if (!(Objects.equals(exerciseName, "Bench Press") || Objects.equals(exerciseName, "Squat") || Objects.equals(exerciseName, "Dead Lift"))) {
                continue;
            }

            // list of exercise sets (1-10)
            List<ExerciseSet> exerciseSets = exercise.getExerciseSets();

            // go through each set and find highest weight
            for (ExerciseSet exerciseSet : exerciseSets) {
                // get weight for each exercise set
                double weight = exerciseSet.getWeight(); // Retrieve the weight for each exercise set
                int reps = exerciseSet.getNumberOfReps();

                // compare weight with current max
                if (Objects.equals(exerciseName, "Bench Press")) {
                    if (weight > benchMax) {
                        benchMax = weight;
                        benchReps = reps;
                    }
                } else if (Objects.equals(exerciseName, "Squat")) {
                    if (weight > squatMax) {
                        squatMax = weight;
                        squatReps = reps;
                    }
                } else if (Objects.equals(exerciseName, "Dead Lift")) {
                    if (weight > deadliftMax) {
                        deadliftMax = weight;
                        deadliftReps = reps;
                    }
                }
            }
        }

        // display the PRs for each exercise
        benchText.setText(String.format("%.2f lb x %d", benchMax, benchReps));
        squatText.setText(String.format("%.2f lb x %d", squatMax, squatReps));
        deadliftText.setText(String.format("%.2f lb x %d", deadliftMax, deadliftReps));

        // Find the Spinner view by its ID
        Spinner spinner_menu = findViewById(R.id.spinner_menu);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Stats activity", "Profile", "Workout History", "Settings", "Add Exercise", "Workout plan", "Random workout"};


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
                        Intent option1Intent = new Intent(StatsActivity.this, ProfileSetup.class);
                        startActivity(option1Intent);
                        break;
                    case "Workout History":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(StatsActivity.this, WorkoutHistory.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(StatsActivity.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Settings":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(StatsActivity.this, Settings.class);
                        startActivity(option4Intent);
                        break;
                    case "Workout plan":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(StatsActivity.this, WorkoutPlan.class);
                        startActivity(option5Intent);
                        break;
                    case "Random workout":
                        Intent option6Intent = new Intent(StatsActivity.this, RandomWorkout.class);
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

    public void onClickHomePage(View view) {
        Intent intent = new Intent(StatsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}