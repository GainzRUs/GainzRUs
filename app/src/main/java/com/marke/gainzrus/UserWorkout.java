package com.marke.gainzrus;

public class UserWorkout {
    String exerciseName, date;
    int reps, sets, weight;

    public UserWorkout(String exerciseName, int reps, int sets, int weight, String date) {
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.date = date;
    }

    @Override
    public String toString() {
        return exerciseName;
    }
}
