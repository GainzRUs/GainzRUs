package com.marke.gainzrus;

import java.util.Date;
import java.util.List;      // For working with lists
import java.util.ArrayList; // For using ArrayList as the list implementation




public class Exercise {
    private String exerciseName;
    private String exerciseRating;
    private Date dateCreated;
    private List<ExerciseSet> exerciseSets; // Collection of sets

    public Exercise(String exerciseName, String exerciseRating) {
        this.exerciseName = exerciseName;
        this.exerciseRating = exerciseRating;
        this.exerciseSets = new ArrayList<>();
    }

    public Exercise(String exerciseName, String exerciseRating, Date dateCreated, List<ExerciseSet> exerciseSets) {
        this.exerciseName = exerciseName;
        this.dateCreated = dateCreated;
        this.exerciseSets = exerciseSets;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public String getExerciseRating(){ return exerciseRating; };
    public Date getDate(){return dateCreated;}

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void addExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
    }
}
