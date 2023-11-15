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

//    TextView benchText = findViewById(R.id.benchValue_text_view);
//    TextView squatText = findViewById(R.id.squatValue_text_view);
//    TextView deadliftText = findViewById(R.id.deadliftValue_text_view);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().setTitle("Statistics");

        Button button;
        button = findViewById(R.id.konfetti_button);

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

        int[] benchArray = new int[10];
        int[] squatArray = new int[10];
        int[] deadliftArray = new int[10];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (i == 0)
                {
                    benchArray[j] = (int) (Math.random() * 100);
                }
                else if (i == 0)
                {
                    squatArray[j] = (int) (Math.random() * 100);
                }
                else
                {
                    deadliftArray[j] = (int) (Math.random() * 100);
                }
            }
        }
    }
}