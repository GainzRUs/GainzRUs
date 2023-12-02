package com.marke.gainzrus;
import java.util.ArrayList;
import java.util.List;
public class ExerciseManager {
    private List<Exercise> exerciseList = new ArrayList<>();

    private ExerciseManager() {
        // Private constructor to prevent external instantiation
    }

    public static ExerciseManager getInstance() {
        return ExerciseManagerHolder.INSTANCE;
    }

    private static class ExerciseManagerHolder {
        private static final ExerciseManager INSTANCE = new ExerciseManager();
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void addExercise(Exercise exercise) {
        exerciseList.add(exercise);
    }

    public void clearExerciseList() {
        exerciseList.clear();
    }

}
