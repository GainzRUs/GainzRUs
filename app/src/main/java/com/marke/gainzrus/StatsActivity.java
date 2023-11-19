package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
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
                            .position(0.5, 0.25, 1, 1)
                            .sizes(new Size(8, 50, 10))
                            .timeToLive(1000)
                            .fadeOutEnabled(true)
                            .build()
            );
        });

        // variable declarations for 3 PRs
        double benchMax = 0.00, squatMax = 0.00, deadliftMax = 0.00;

        // Fetch exercise data from the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        List<Exercise> allExercises = dbHelper.getAllExercises();

        // go through exercises in database
        for (Exercise exercise : allExercises) {
            String exerciseName = exercise.getExerciseName().trim();

            // if exercise isn't one of these go to next exercise in database
            if (!(Objects.equals(exerciseName, "Bench") || Objects.equals(exerciseName, "Squat") || Objects.equals(exerciseName, "DeadLift"))) {
                continue;
            }

            // list of exercise sets (1-10)
            List<ExerciseSet> exerciseSets = exercise.getExerciseSets();

            // go through each set and find highest weight
            for (ExerciseSet exerciseSet : exerciseSets) {
                // get weight for each exercise set
                double weight = exerciseSet.getWeight(); // Retrieve the weight for each exercise set

                // compare weight with current max
                if (Objects.equals(exerciseName, "Bench")) {
                    benchMax = Math.max(benchMax, weight);
                } else if (Objects.equals(exerciseName, "Squat")) {
                    squatMax = Math.max(squatMax, weight);
                } else if (Objects.equals(exerciseName, "DeadLift")) {
                    deadliftMax = Math.max(deadliftMax, weight);
                }
            }
        }

        // display the PRs for each exercise
        benchText.setText(benchMax + "lb");
        squatText.setText(squatMax + " lb");
        deadliftText.setText(deadliftMax + " lb");
    }
}