package com.marke.gainzrus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class DailyExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> dates;
    private final Map<String, List<WorkoutWithExercises>> organizedWorkouts;

    public DailyExpandableListAdapter(Context context, List<String> dates, Map<String, List<WorkoutWithExercises>> organizedWorkouts) {
        this.context = context;
        this.dates = dates;
        this.organizedWorkouts = organizedWorkouts;
    }

    @Override
    public int getGroupCount() {
        return dates.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Get the list of workouts for the specified date
        String date = dates.get(groupPosition);
        List<WorkoutWithExercises> workouts = organizedWorkouts.get(date);

        // Return the count of workouts for the date
        return (workouts != null) ? workouts.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dates.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // Get the list of workouts for the specified date
        String date = dates.get(groupPosition);
        List<WorkoutWithExercises> workouts = organizedWorkouts.get(date);

        // Return the workout for the specified child position
        return (workouts != null && childPosition < workouts.size()) ? workouts.get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView listHeader = convertView.findViewById(R.id.listHeader);
        String dateLabel = (String) getGroup(groupPosition);

        // Assuming each workout has the same rating, you can get it from the first workout
        List<WorkoutWithExercises> workouts = organizedWorkouts.get(dateLabel);

        // Concatenate all ratings
        StringBuilder ratingsBuilder = new StringBuilder();
        for (WorkoutWithExercises workout : workouts) {
            ratingsBuilder.append(workout.getWorkoutRating()).append(" ");
        }

        // Set the text with concatenated ratings
        listHeader.setText("    " + dateLabel + " " + ratingsBuilder.toString());

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView listItem = convertView.findViewById(R.id.listItem);
        WorkoutWithExercises workout = (WorkoutWithExercises) getChild(groupPosition, childPosition);

        if (workout != null) {
            StringBuilder exerciseDetails = new StringBuilder();

            // Iterate through exercises and append details
            for (Exercise exercise : workout.getExercises()) {
                exerciseDetails.append("Exercise: ").append(exercise.getExerciseName()).append("\n");

                // Iterate through sets and append details
                for (ExerciseSet set : exercise.getExerciseSets()) {
                    exerciseDetails.append("  Set: Reps=").append(set.getNumberOfReps()).append(", Weight=").append(set.getWeight()).append("\n");
                }
            }

            // Set the text with exercise details
            listItem.setText(exerciseDetails.toString());
        }

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

