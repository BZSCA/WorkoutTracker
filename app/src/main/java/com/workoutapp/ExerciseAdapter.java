package com.workoutapp;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private Exercise exercise;
    private final ExerciseAdapterInterface eaInterface;
    private Context context;

    // data is passed into the constructor
    ExerciseAdapter(WorkoutFragment fragment, Exercise exercise) {
        this.exercise = exercise;
        eaInterface = fragment;
        this.context = MyApp.getContext();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_exercise, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setNumberColor(exercise.get(position).getWeight(), holder.weight);
        setNumberColor(exercise.get(position).getReps(), holder.reps);

        if (position > exercise.getCurrent()) {
            disableEditText(holder.weight);
            disableEditText(holder.reps);

//            holder.weight.setTextColor(ContextCompat.getColor(context, R.color.lightGrey));
//            holder.reps.setTextColor(ContextCompat.getColor(context, R.color.lightGrey));

        } else {
            enableEditText(holder.weight);
            enableEditText(holder.reps);

        }

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return exercise.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnLongClickListener*/ {
        EditText weight;
        EditText reps;

        ViewHolder(View itemView) {
            super(itemView);

            weight = itemView.findViewById(R.id.weight);
            weight.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        try {
                            int position = getBindingAdapterPosition();
                            exercise.setWeight(position, Double.parseDouble(weight.getText().toString()));
                            if (exercise.get(position).getState() && position == exercise.getCurrent()) {
                                exercise.progressCurrent();
                                eaInterface.startTimer();
                                notifyItemChanged(position);
                                notifyItemChanged(position + 1);
                            }
                        } catch (NumberFormatException e) {
                        }

                        return true;
                    }
                    return false;
                }
            });
            weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    double w = exercise.get(getBindingAdapterPosition()).getWeight();

                    if (hasFocus) {
                        if (w < 0)
                            weight.setText("");
                    } else {
                        if (w < 0) {
                            setNumberColor(w, weight);
                        }

                    }
                }
            });

            reps = itemView.findViewById(R.id.reps);
            reps.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        try {
                            int position = getBindingAdapterPosition();
                            exercise.setReps(position, Integer.parseInt(reps.getText().toString()));
                            if (exercise.get(position).getState() && position == exercise.getCurrent()) {
                                exercise.progressCurrent();
                                eaInterface.startTimer();
                                notifyItemChanged(position);
                                notifyItemChanged(position + 1);
                            }
                        } catch (NumberFormatException e) {
                        }

                        return true;
                    }
                    return false;
                }
            });

            reps.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    //Log.i("adapter position", String.valueOf(getBindingAdapterPosition()));
                    int r = exercise.get(getBindingAdapterPosition()).getReps();

                    if (hasFocus) {
                        if (r < 0)
                            reps.setText("");
                    } else {
                        if (r < 0) {
                            setNumberColor(r, reps);
                        }

                    }
                }
            });
        }
    }

    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        //editText.setKeyListener(null);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        //editText.setKeyListener(null);
    }

    private void setNumberColor(int i, EditText editText) {
        if (i >= 0) {
            editText.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            editText.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }
        editText.setText(String.valueOf(Math.abs(i)));

    }

    private void setNumberColor(double d, EditText editText) {
        if (d > 0) {
            editText.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            editText.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }

        if (d % 1 == 0) {
            editText.setText(String.valueOf(Math.abs((int) d)));
        } else {
            editText.setText(String.valueOf(Math.abs(d)));
        }

    }

    interface ExerciseAdapterInterface {
        void startTimer();
    }


}
