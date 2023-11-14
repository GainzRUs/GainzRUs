package com.marke.gainzrus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";

    public static final String SETS_TABLE = "SETS_TABLE";
    public static final String COLUMN_SET_ID = "SET_ID";
    public static final String COLUMN_EXERCISE_ID_FK = "EXERCISE_ID"; // Foreign key linking to EXERCISE_TABLE
    public static final String COLUMN_NUMBER_OF_REPS = "NUMBER_OF_REPS";
    public static final String COLUMN_WEIGHT = "WEIGHT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "workout.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Exercise Table
        String createExerciseTableStatement = "CREATE TABLE " + EXERCISE_TABLE + " (" +
                COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_NAME + " TEXT)";
        db.execSQL(createExerciseTableStatement);

        // Create Sets Table
        String createSetsTableStatement = "CREATE TABLE " + SETS_TABLE + " (" +
                COLUMN_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_ID_FK + " INTEGER, " +
                COLUMN_NUMBER_OF_REPS + " INTEGER, " +
                COLUMN_WEIGHT + " REAL, " +
                "FOREIGN KEY (" + COLUMN_EXERCISE_ID_FK + ") REFERENCES " + EXERCISE_TABLE + "(" + COLUMN_EXERCISE_ID + "))";
        db.execSQL(createSetsTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SETS_TABLE);

        // Recreate tables
        onCreate(db);
    }


    // Method to add an exercise and its sets
    public boolean addExerciseWithSets(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put(COLUMN_EXERCISE_NAME, exercise.getExerciseName());
        long exerciseId = db.insert(EXERCISE_TABLE, null, exerciseValues);

        if (exerciseId == -1) {
            return false; // Failed to insert exercise
        }

        for (ExerciseSet set : exercise.getExerciseSets()) {
            ContentValues setsValues = new ContentValues();
            setsValues.put(COLUMN_EXERCISE_ID_FK, exerciseId);
            setsValues.put(COLUMN_NUMBER_OF_REPS, set.getNumberOfReps());
            setsValues.put(COLUMN_WEIGHT, set.getWeight());
            long setsId = db.insert(SETS_TABLE, null, setsValues);

            if (setsId == -1) {
                return false; // Failed to insert sets
            }
        }

        return true; // Return true if both exercise and sets are inserted successfully
    }
}
