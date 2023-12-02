package com.marke.gainzrus;

import java.util.List;

public class WorkoutWithExercises {
    private int workoutId;
    private String workoutRating;
    private String workoutDate;
    private List<Exercise> exercises;

    public WorkoutWithExercises(int workoutId, String workoutRating, String workoutDate, List<Exercise> exercises) {
        this.workoutId = workoutId;
        this.workoutRating = workoutRating;
        this.workoutDate = workoutDate;
        this.exercises = exercises;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public String getWorkoutRating() {
        return workoutRating;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
