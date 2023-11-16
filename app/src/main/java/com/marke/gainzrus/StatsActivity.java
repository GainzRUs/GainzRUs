package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
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

        // dummy random max data for 3 exercises
        int[] benchArray = new int[10];
        int[] squatArray = new int[10];
        int[] deadliftArray = new int[10];

        for (int i = 0; i < 10; i++)
        {
            benchArray[i] = (int) (Math.random() * 175) + 1;
            squatArray[i] = (int) (Math.random() * 350) + 1;
            deadliftArray[i] = (int) (Math.random() * 600) + 1;
        }

        double benchMax = 0, squatMax = 0, deadliftMax = 0;

        // find maxes from exercise arrays
        for (int i = 0; i < 10; i++)
        {
            if (benchArray[i] > benchMax)
            {
                benchMax = benchArray[i];
            }
            if (squatArray[i] > squatMax)
            {
                squatMax = squatArray[i];
            }
            if (deadliftArray[i] > deadliftMax)
            {
                deadliftMax = deadliftArray[i];
            }
        }

        // set textviews to hold max values
        benchText.setText(benchMax + " lb");
        squatText.setText(squatMax + " lb");
        deadliftText.setText(deadliftMax + " lb");
    }
}