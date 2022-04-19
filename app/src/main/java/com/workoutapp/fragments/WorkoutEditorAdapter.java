package com.workoutapp.fragments;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.workoutapp.R;
import com.workoutapp.dataclasses.ExerciseData;

import java.util.ArrayList;

public class WorkoutEditorAdapter extends RecyclerView.Adapter<WorkoutEditorAdapter.ViewHolder> {

    private ArrayList<ExerciseData> exerciseDataList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    WorkoutEditorAdapter(Context context, ArrayList<ExerciseData> exerciseDataList) {
        this.mInflater = LayoutInflater.from(context);
        this.exerciseDataList = exerciseDataList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rvitem_workout_editor, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.exerciseName.setText(exerciseDataList.get(position).getName());
        holder.sets.setText(String.valueOf(exerciseDataList.get(position).getSets()));
        holder.reps.setText(String.valueOf(exerciseDataList.get(position).getReps()));
        holder.rest.setText(String.valueOf(exerciseDataList.get(position).getRest()));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (exerciseDataList != null) {
            return exerciseDataList.size();
        }
        Log.e("Object null", "Exercise data");
        return -1;
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

            exerciseName.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        int position = getBindingAdapterPosition();
                        exerciseDataList.get(position).setName(String.valueOf(exerciseName.getText()));
                        notifyItemChanged(position);
                    }
                    return true;
                }
            });

            sets.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        int position = getBindingAdapterPosition();
                        exerciseDataList.get(position).setSets(Double.parseDouble(String.valueOf(sets.getText())));
                        notifyItemChanged(position);
                    }
                    return true;
                }
            });

            reps.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        int position = getBindingAdapterPosition();
                        exerciseDataList.get(position).setReps(Integer.parseInt(String.valueOf(sets.getText())));
                        notifyItemChanged(position);
                    }
                    return true;
                }
            });

            rest.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        int position = getBindingAdapterPosition();
                        exerciseDataList.get(position).setRest(Integer.parseInt(String.valueOf(rest.getText())));
                        notifyItemChanged(position);
                    }
                    return true;
                }
            });


        }
    }

    public void saveAll(){
        //get all recycler view items and update exerciseData
    }

    public void addExercise() {
        this.exerciseDataList.add(new ExerciseData());
        notifyItemInserted(exerciseDataList.size() - 1);
    }

    public void removeExercise() {
        this.exerciseDataList.remove(exerciseDataList.size() - 1);
        notifyItemRemoved(exerciseDataList.size());
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}