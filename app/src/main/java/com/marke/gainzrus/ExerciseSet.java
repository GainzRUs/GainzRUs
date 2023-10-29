package com.marke.gainzrus;

public class ExerciseSet {
    private int numberOfReps;
    private double weight;

    public ExerciseSet(int numberOfReps, double weight) {
        this.numberOfReps = numberOfReps;
        this.weight = weight;
    }

    public int getNumberOfReps() {
        return numberOfReps;
    }

    public double getWeight() {
        return weight;
    }

    public void setNumberOfReps(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
