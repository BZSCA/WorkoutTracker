package com.workoutapp.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.dataclasses.ExerciseData;
import com.workoutapp.dataclasses.Workout;
import com.workoutapp.dataclasses.Workout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardWorkoutPreviewAdapter extends ArrayAdapter<ExerciseData> {

    private ArrayList<ExerciseData> exerciseDataArrayList;

    // data is passed into the constructor
    public CardWorkoutPreviewAdapter(ArrayList<ExerciseData> exerciseDataArrayList) {
        super(MyApp.getContext(), R.layout.lvitem_workoutcard, exerciseDataArrayList);
        this.exerciseDataArrayList = exerciseDataArrayList;
    }


    @Override
    public int getCount(){
        return exerciseDataArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        final View result;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.lvitem_workoutcard, parent, false);

        }

        ExerciseData exerciseData = getItem(position);

        if (exerciseData != null){
            TextView name = view.findViewById(R.id.cardExerciseName);
            TextView sets = view.findViewById(R.id.cardSets);
            TextView reps = view.findViewById(R.id.cardReps);
            TextView rest = view.findViewById(R.id.cardRest);

            name.setText(exerciseData.getName());
            sets.setText(String.valueOf(exerciseData.getSets()));
            rest.setText(String.valueOf(exerciseData.getRest()));
            reps.setText(String.valueOf(exerciseData.getReps()));
        }

        return view;
    }

}
