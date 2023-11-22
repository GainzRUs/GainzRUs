package com.marke.gainzrus;

import java.util.Date;
import java.util.List;      // For working with lists
import java.util.ArrayList; // For using ArrayList as the list implementation




public class Exercise {
    private String exerciseName;
    private Date dateCreated;
    private List<ExerciseSet> exerciseSets; // Collection of sets

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
        this.exerciseSets = new ArrayList<>();
    }

    public Exercise(String exerciseName, Date dateCreated, List<ExerciseSet> exerciseSets) {
        this.exerciseName = exerciseName;
        this.dateCreated = dateCreated;
        this.exerciseSets = exerciseSets;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public Date getDate(){return dateCreated;}

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void addExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
    }
}
