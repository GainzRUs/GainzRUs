package com.marke.gainzrus;

import java.util.Date;
import java.util.List;      // For working with lists
import java.util.ArrayList; // For using ArrayList as the list implementation




public class Exercise {
    private String exerciseName;

    private List<ExerciseSet> exerciseSets; // Collection of sets

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;

        this.exerciseSets = new ArrayList<>();
    }

    public Exercise(String exerciseName,  List<ExerciseSet> exerciseSets) {
        this.exerciseName = exerciseName;
        this.exerciseSets = exerciseSets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void addExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
    }
}
