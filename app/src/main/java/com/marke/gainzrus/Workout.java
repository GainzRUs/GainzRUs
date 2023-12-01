package com.marke.gainzrus;

import java.util.Date;

public class Workout {
    private long workoutId;
    private String workoutRating;
    private Date workoutDate;

    public String getWorkoutRating(){ return workoutRating;}

    public long getWorkoutId() {
        return workoutId;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public void setWorkoutRating(String workoutRating) {
        this.workoutRating = workoutRating;
    }
}
