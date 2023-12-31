package com.marke.gainzrus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MonthlyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> exerciseCollection;
    private List<String> monthList;

    public MonthlyExpandableListAdapter(Context context, List<String> monthList, Map<String, List<String>> exerciseCollection) {
        this.context = context;
        this.monthList = monthList;
        this.exerciseCollection = exerciseCollection;
    }

    @Override
    public int getGroupCount() {
        return exerciseCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return exerciseCollection.get(monthList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return monthList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return exerciseCollection.get(monthList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String monthName = monthList.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.month_expandable_item, null);
        }
        TextView item = view.findViewById(R.id.textview_month_year);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(monthName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String workout = getChild(i, i1).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_item, null);
        }
        TextView item = view.findViewById(R.id.textview_exercise_info);
        ImageView delete = view.findViewById(R.id.imageview_delete);
        item.setText(workout);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove this item?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        List<String> child = exerciseCollection.get(monthList.get(i));
                        child.remove(i1);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
