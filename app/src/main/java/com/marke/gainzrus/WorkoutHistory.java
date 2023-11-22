package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutHistory extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Map<String, List<String>> exerciseCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        getSupportActionBar().setTitle("Workout History");

        // Fetch exercise data from the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        List<Exercise> allExercises = dbHelper.getAllExercises();

        // Organize exercises by month
        exerciseCollection = organizeExercisesByMonth(allExercises);

        // Set up the ExpandableListView
        expandableListView = findViewById(R.id.expandable_list_view_workouts);
        expandableListAdapter = new MonthlyExpandableListAdapter(this, new ArrayList<>(exerciseCollection.keySet()), exerciseCollection);
        expandableListView.setAdapter(expandableListAdapter);

        // Add event listeners if needed
    }

    private Map<String, List<String>> organizeExercisesByMonth(List<Exercise> allExercises) {
        Map<String, List<String>> organizedCollection = new HashMap<>();

        for (Exercise exercise : allExercises) {
            // Extract month from the exercise date and use it as a key
            // Modify the following line based on how your Exercise object stores the date
            String month = ""; // Extract month from exercise date (e.g., "January")

            List<String> exerciseList = organizedCollection.get(month);
            if (exerciseList == null) {
                exerciseList = new ArrayList<>();
            }

            // Add exercise name to the list
            exerciseList.add(exercise.getExerciseName());

            // Update the map with the organized list
            organizedCollection.put(month, exerciseList);
        }

        return organizedCollection;
    }
}
