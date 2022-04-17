package com.workoutapp;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutEditorFragment extends Fragment {

    Workout workout;
    WorkoutEditorAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Workout";

    public WorkoutEditorFragment() {
        // Required empty public constructor
    }

    public static WorkoutEditorFragment newInstance(Workout workout) {
        WorkoutEditorFragment fragment = new WorkoutEditorFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, workout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workout = (Workout) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_editor, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvWorkoutEditor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
                return false;
            }
        });

        adapter = new WorkoutEditorAdapter(getContext(), workout.getExerciseData());
        recyclerView.setAdapter(adapter);


        view.findViewById(R.id.saveWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.saveAll();
                workout.updateWorkout();
            }
        });

        view.findViewById(R.id.removeExercise).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                adapter.removeExercise();
            }
        });

        view.findViewById(R.id.addExercise).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                adapter.addExercise();
            }
        });

        return view;
    }


}