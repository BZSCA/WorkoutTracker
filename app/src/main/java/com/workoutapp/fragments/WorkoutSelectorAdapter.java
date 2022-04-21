package com.workoutapp.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.Utility;
import com.workoutapp.dataclasses.ExerciseData;
import com.workoutapp.dataclasses.Routine;
import com.workoutapp.dataclasses.Workout;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class WorkoutSelectorAdapter extends RecyclerView.Adapter<WorkoutSelectorAdapter.ViewHolder> {

    private Routine routine;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    WorkoutSelectorAdapter(Context context, Routine routine) {
        this.mInflater = LayoutInflater.from(context);
        this.routine = routine;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.workout_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(routine.getWorkouts().get(position).getName());

        Log.i("lenght of exercise data", String.valueOf(routine.getWorkouts().get(position).getExerciseData().size()));
        CardWorkoutPreviewAdapter adapter = new CardWorkoutPreviewAdapter(routine.getWorkouts().get(position).getExerciseData());
        holder.listView.setAdapter(adapter);

        Utility.setListViewHeightBasedOnChildren(holder.listView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return routine.getWorkouts().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ListView listView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.cardWorkoutName);
            itemView.setOnClickListener(this);

            listView = itemView.findViewById(R.id.workoutPreview);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getBindingAdapterPosition());
        }

    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
