package com.workoutapp;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {

    private static final String ARG_PARAM1 = "exercise";

    View view;
    Exercise exercise;
    ExerciseAdapter adapter;


    public ExerciseFragment() {
        // Required empty public constructor
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public static ExerciseFragment newInstance(Exercise exercise) {
        ExerciseFragment fragment = new ExerciseFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, exercise);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            exercise = (Exercise) getArguments().getSerializable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        // Inflate the layout for this fragment
        TextView textView = view.findViewById(R.id.taskName);
        textView.setText(exercise.getName());

        TextView timerText = view.findViewById(R.id.timer);
        RecyclerView recyclerView = view.findViewById(R.id.fragmentRvItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
                return false;
            }
        });


        adapter = new ExerciseAdapter(((WorkoutFragment) ExerciseFragment.this.getParentFragment()), exercise);
        recyclerView.setAdapter(adapter);
        //setupOnSwipeListener(view);
        return view;

    }

    @Override
    public void onPause(){
        super.onPause();

    }

//    private void setupOnSwipeListener(View view){
//        view.setOnTouchListener(new OnSwipeTouchListener(MyApp.getContext()) {
//            @Override
//            public void onSwipeLeft(){
//                Toast.makeText(MyApp.getContext(),"Swipe left", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeRight(){
//                Toast.makeText(MyApp.getContext(),"Swipe right", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }

}