package com.marke.gainzrus;

import java.text.SimpleDateFormat;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide app title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.rocky);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfTheWeek;

        switch (day) {
            case Calendar.SUNDAY:
                dayOfTheWeek = "Sunday";
                break;
            case Calendar.MONDAY:
                dayOfTheWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfTheWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfTheWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfTheWeek = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfTheWeek = "Friday";
                break;
            default:
                dayOfTheWeek = "Saturday";
                break;
        }
        //create a date string
        String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview.
        TextView date = (TextView) findViewById(R.id.date_text_view);
        //set it as current date.
        date.setText(dayOfTheWeek + " " + currentDate);

        String[] motivationalQuotes = new String[]
                {
                        "Success is not final; failure is not fatal: It is the courage to continue that counts. — Winston S. Churchill",
                        "It is better to fail in originality than to succeed in imitation. — Herman Melville",
                        "The road to success and the road to failure are almost exactly the same. — Colin R. Davis",
                        "Success usually comes to those who are too busy looking for it. — Henry David Thoreau",
                        "Develop success from failures. Discouragement and failure are two of the surest stepping stones to success. —Dale Carnegie",
                        "Anything lost can be found again except for time wasted. A vision without action is merely a dream. —Kevin Gates",
                        "Those who say they can't and those who say they can are both usually right. —Henry Ford",
                        "I never dreamed about success. I worked for it. —Estée Lauder",
                        "Success is getting what you want, happiness is wanting what you get. ―W. P. Kinsella"
                };

        // use random number to choose motivational quote and display it
        Random random = new Random();
        int randomIndex = random.nextInt(9);
        String quoteMessage = motivationalQuotes[randomIndex];
        TextView quote = findViewById(R.id.workoutType_text_view);
        quote.setText(quoteMessage);
    }

    // The click handler method defined in the XML layout
    public void onClickViewWorkout(View view) {
        Intent intent = new Intent(MainActivity.this, WorkoutHistory.class);
        startActivity(intent);
    }


    public void onClickUserProfile(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileSetup.class);
        startActivity(intent);
    }

    public void onAddExerciseClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddExercise.class);
        startActivity(intent);
    }

    public void onWorkoutPlanClick(View view) {
        Intent intent = new Intent(MainActivity.this, WorkoutPlan.class);
        startActivity(intent);
    }


    public void onClickPRStats(View view) {
        Intent intent = new Intent(MainActivity.this, StatsActivity.class);
        startActivity(intent);
    }

    public void onClickUserSettings(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    public void onClickRandomWorkout(View view) {
        Intent intent = new Intent(MainActivity.this, RandomWorkout.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.stop();
        backgroundMusic.release();
    }
}
