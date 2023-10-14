package com.marke.gainzrus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutHistory extends AppCompatActivity {

    List<String> monthList;
    List<String> exerciseList;
    Map<String, List<String>> exerciseCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        createMonthList();
        createCollection();
        expandableListView = findViewById(R.id.expandable_list_view_workouts);
        expandableListAdapter = new MonthlyExpandableListAdapter(this, monthList, exerciseCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition)
                {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createMonthList() {
        monthList = new ArrayList<>();
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("Arpil");
        monthList.add("May");
        monthList.add("June");
    }

    private void createCollection() {
        String[] januaryWorkouts = {"Bench", "Squat", "Cardio"};
        String[] februaryWorkouts = {"Bench", "Squat", "Cardio"};
        String[] marchWorkouts = {"Bench", "Squat", "Cardio"};
        String[] aprilWorkouts = {"Bench", "Squat", "Cardio"};
        String[] mayWorkouts = {"Bench", "Squat", "Cardio"};
        String[] juneWorkouts = {"Bench", "Squat", "Cardio"};

        exerciseCollection = new HashMap<String, List<String>>();

        for (String month : monthList) {
            if (month.equals("January")) {
                loadChild(januaryWorkouts);
            } else if (month.equals("February")) {
                loadChild(februaryWorkouts);
            } else if (month.equals("March")) {
                loadChild(marchWorkouts);
            } else if (month.equals("Arpil")) {
                loadChild(aprilWorkouts);
            } else if (month.equals("May")) {
                loadChild(mayWorkouts);
            } else if (month.equals("june")) {
                loadChild(juneWorkouts);
            }
            exerciseCollection.put(month, exerciseList);
        }
    }

    private void loadChild(String[] workouts) {
        exerciseList = new ArrayList<>();
        for (String workout : workouts) {
            exerciseList.add(workout);
        }
    }
}