package com.workoutapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment implements ExerciseAdapter.ExerciseAdapterInterface{

    private View view;
    private Workout workout;
    private ArrayList<ExerciseFragment> fragments;
    private TextView timerText;
    int current;
    private CountDownTimer timer;

    private static final String ARG_PARAM1 = "workout";
    private static final String ARG_PARAM2 = "current";

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public static WorkoutFragment newInstance(Workout workout) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, workout);
        args.putSerializable(ARG_PARAM2, 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            current = getArguments().getInt(ARG_PARAM2);
            workout = (Workout) getArguments().getSerializable(ARG_PARAM1);
        }

        fragments = new ArrayList<>();

        for (Exercise e : workout.getExercises()) {
            fragments.add(ExerciseFragment.newInstance(e));
            e.addObserver(numChanged);
        }
        replaceFragment(fragments.get(0));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_workout, container, false);
        timerText = view.findViewById(R.id.timer);

        view.findViewById(R.id.prevButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0){
                    replaceFragment(fragments.get(current - 1));
                }
                current = current - 1;
            }
        });

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current + 1 < fragments.size()){
                    replaceFragment(fragments.get(current + 1));
                }
                current = current + 1;
            }
        });

        view.findViewById(R.id.cancelTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                timerText.setText(R.string._000);
            }
        });



        return view;
    }

    private void replaceFragment(ExerciseFragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.exerciseLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(workout.getExercises().get(current).getRest()* 1000L, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timerText.setText("done!");
            }

        }.start();
    }

    private Observer numChanged = new Observer() {
        @Override
        public void update(Observable o, Object newValue) {
            if (workout != null) {
                workout.saveSelf();
            }
        }
    };

}