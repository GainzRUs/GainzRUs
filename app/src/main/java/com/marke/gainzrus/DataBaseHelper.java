package com.marke.gainzrus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";

    public static final String SETS_TABLE = "SETS_TABLE";
    public static final String COLUMN_SET_ID = "SET_ID";
    public static final String COLUMN_EXERCISE_ID_FK = "EXERCISE_ID"; // Foreign key linking to EXERCISE_TABLE
    public static final String COLUMN_NUMBER_OF_REPS = "NUMBER_OF_REPS";
    public static final String COLUMN_WEIGHT = "WEIGHT";
    public static final String COLUMN_DATE_CREATED = "DATE_CREATED";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "workout.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Exercise Table
        String createExerciseTableStatement = "CREATE TABLE " + EXERCISE_TABLE + " (" +
                COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_NAME + " TEXT, " +
                COLUMN_DATE_CREATED + " TEXT)"; // Add dateCreated column
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
        exerciseValues.put(COLUMN_DATE_CREATED, getCurrentDateTime());
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


    // In DataBaseHelper class
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to retrieve all exercises
        String query = "SELECT * FROM " + EXERCISE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and populate the list
        if (cursor.moveToFirst()) {
            do {
                // Check if the column index is valid
                int columnIndex = cursor.getColumnIndex(COLUMN_EXERCISE_ID);
                int exerciseId = (columnIndex != -1) ? cursor.getInt(columnIndex) : -1;

                columnIndex = cursor.getColumnIndex(COLUMN_EXERCISE_NAME);
                String exerciseName = (columnIndex != -1) ? cursor.getString(columnIndex) : "";

                columnIndex = cursor.getColumnIndex(COLUMN_DATE_CREATED);
                String dateCreated = (columnIndex != -1) ? cursor.getString(columnIndex) : "";

                // Parse the date string into a Date object
                Date parsedDate = null;
                if (!dateCreated.isEmpty()) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        parsedDate = dateFormat.parse(dateCreated);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle the exception if the date string cannot be parsed
                    }
                }


                // Query to retrieve sets for the current exercise
                String setsQuery = "SELECT * FROM " + SETS_TABLE +
                        " WHERE " + COLUMN_EXERCISE_ID_FK + " = ?";
                Cursor setsCursor = db.rawQuery(setsQuery, new String[]{String.valueOf(exerciseId)});

                List<ExerciseSet> exerciseSets = new ArrayList<>();
                while (setsCursor.moveToNext()) {
                    // Check if the column index is valid for reps
                    int repsColumnIndex = setsCursor.getColumnIndex(COLUMN_NUMBER_OF_REPS);
                    int reps = (repsColumnIndex != -1) ? setsCursor.getInt(repsColumnIndex) : 0;

                    // Check if the column index is valid for weight
                    int weightColumnIndex = setsCursor.getColumnIndex(COLUMN_WEIGHT);
                    double weight = (weightColumnIndex != -1) ? setsCursor.getDouble(weightColumnIndex) : 0.0;

                    exerciseSets.add(new ExerciseSet(reps, weight));
                }
                setsCursor.close();

                exercises.add(new Exercise(exerciseName,  parsedDate, exerciseSets));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return exercises;
    }



    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // for user to clear data
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + EXERCISE_TABLE);
        db.execSQL("DELETE FROM " + SETS_TABLE);
        db.execSQL("VACUUM");
    }
}
