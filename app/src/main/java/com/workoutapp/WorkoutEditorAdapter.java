package com.workoutapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutEditorAdapter extends RecyclerView.Adapter<WorkoutEditorAdapter.ViewHolder> {

    private ArrayList<ExerciseData> exerciseData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    WorkoutEditorAdapter(Context context, ArrayList<ExerciseData> exerciseData) {
        this.mInflater = LayoutInflater.from(context);
        this.exerciseData = exerciseData;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.workout_editor_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.exerciseName.setText(exerciseData.get(position).getName());
        holder.sets.setText(String.valueOf(exerciseData.get(position).getSets()));
        holder.reps.setText(String.valueOf(exerciseData.get(position).getReps()));
        holder.rest.setText(String.valueOf(exerciseData.get(position).getRest()));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return exerciseData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText exerciseName;
        EditText sets;
        EditText reps;
        EditText rest;

        ViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            sets = itemView.findViewById(R.id.sets);
            reps = itemView.findViewById(R.id.reps);
            rest = itemView.findViewById(R.id.rest);
        }


    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}