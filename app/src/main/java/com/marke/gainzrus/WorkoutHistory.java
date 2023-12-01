package com.marke.gainzrus;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
}
