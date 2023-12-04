package com.marke.gainzrus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WorkoutHistory extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Map<String, List<WorkoutWithExercises>> exerciseCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        getSupportActionBar().setTitle("Workout History");

        // Fetch exercise data from the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);

        // Log the results in JSON-like format
        logWorkouts(dbHelper.getAllWorkoutsWithExercises());

        List<WorkoutWithExercises> allWorkouts = dbHelper.getAllWorkoutsWithExercises();

        // Organize workouts by day
        exerciseCollection = organizeWorkoutsByDay(allWorkouts);

        // Set up the ExpandableListView
        expandableListView = findViewById(R.id.expandable_list_view_workouts);
        expandableListAdapter = new DailyExpandableListAdapter(this, new ArrayList<>(exerciseCollection.keySet()), exerciseCollection);
        expandableListView.setAdapter(expandableListAdapter);

        // Assuming these are the names of your activities or pages
        String[] pageNames = {"Workout History", "Profile", "Random Workout", "Settings", "Add Exercise", "Workout plan", "Stats activity"};


        Spinner spinner_menu = findViewById(R.id.spinner_menu);
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
                        Intent option1Intent = new Intent(WorkoutHistory.this, ProfileSetup.class);
                        startActivity(option1Intent);
                        break;
                    case "Stats activity":
                        // Navigate to Activity related to Option 2
                        Intent option2Intent = new Intent(WorkoutHistory.this, StatsActivity.class);
                        startActivity(option2Intent);
                        break;
                    case "Add Exercise":
                        // Navigate to Activity related to Option 3
                        Intent option3Intent = new Intent(WorkoutHistory.this, AddExercise.class);
                        startActivity(option3Intent);
                        break;
                    case "Settings":
                        // Navigate to Activity related to Option 4
                        Intent option4Intent = new Intent(WorkoutHistory.this, Settings.class);
                        startActivity(option4Intent);
                        break;
                    case "Workout plan":
                        // Navigate to Activity related to Option 5
                        Intent option5Intent = new Intent(WorkoutHistory.this, WorkoutPlan.class);
                        startActivity(option5Intent);
                        break;
                    case "Random Workout":
                        Intent option6Intent = new Intent(WorkoutHistory.this, RandomWorkout.class);
                        startActivity(option6Intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle scenario when nothing is selected (if needed)
            }
        });
    }

    private Map<String, List<WorkoutWithExercises>> organizeWorkoutsByDay(List<WorkoutWithExercises> allWorkouts) {
        Map<String, List<WorkoutWithExercises>> organizedCollection = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (WorkoutWithExercises workout : allWorkouts) {
            // Extract date from the workout and use it as a key
            String workoutDate = workout.getWorkoutDate(); // Modify this based on your WorkoutWithExercises object

            // Get the current date and workout date as Date objects
            Date currentDate = new Date();
            Date exerciseDate = null;
            try {
                exerciseDate = dateFormat.parse(workoutDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Calculate the difference in days
            long diffInMillies = Math.abs(currentDate.getTime() - exerciseDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            // Determine the label for the date
            String dateLabel;
            if (diffInDays == 0) {
                dateLabel = "Today";
            } else if (diffInDays == 1) {
                dateLabel = "Yesterday";
            } else {
                dateLabel = diffInDays + " days ago";
            }

            List<WorkoutWithExercises> workoutList = organizedCollection.get(dateLabel);
            if (workoutList == null) {
                workoutList = new ArrayList<>();
            }

            workoutList.add(workout);

            // Update the map with the organized list
            organizedCollection.put(dateLabel, workoutList);
        }

        return organizedCollection;
    }





    private void logWorkouts(List<WorkoutWithExercises> workouts) {
        for (WorkoutWithExercises workout : workouts) {
            Log.d("WorkoutHistory", "Workout: " + workout.getWorkoutDate() + ", Rating: " + workout.getWorkoutRating());
            for (Exercise exercise : workout.getExercises()) {
                Log.d("WorkoutHistory", "  Exercise: " + exercise.getExerciseName());
                for (ExerciseSet set : exercise.getExerciseSets()) {
                    Log.d("WorkoutHistory", "    Set: Reps=" + set.getNumberOfReps() + ", Weight=" + set.getWeight());
                }
            }
        }
    }

    public void onClickHomePage(View view) {
        Intent intent = new Intent(WorkoutHistory.this, MainActivity.class);
        startActivity(intent);
    }
}
