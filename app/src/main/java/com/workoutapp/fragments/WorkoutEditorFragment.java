package com.workoutapp.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.dataclasses.Routine;
import com.workoutapp.dataclasses.Workout;

import java.time.DayOfWeek;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutEditorFragment extends Fragment {

    int position;
    Routine routine;
    WorkoutEditorAdapter adapter;
    Spinner spinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "routine";
    private static final String ARG_PARAM2 = "position";

    public WorkoutEditorFragment() {
        // Required empty public constructor
    }

    public static WorkoutEditorFragment newInstance(Routine routine, int position) {
        WorkoutEditorFragment fragment = new WorkoutEditorFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, routine);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            routine = (Routine) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_editor, container, false);

        Workout workout = routine.getWorkouts().get(position);

        EditText workoutName = view.findViewById(R.id.workoutName);
        workoutName.setText(workout.getName());

        workoutName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    workout.setName(String.valueOf(workoutName.getText()));
                }
                return true;
            }
        });

        spinner = view.findViewById(R.id.dayOfWeek);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(MyApp.getContext(), R.array.daysOfWeekArray, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                routine.getWorkouts().get(position).setDayOfWeek(DayOfWeek.values()[i]);
                routine.saveRoutine();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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
                routine.saveRoutine();
                getParentFragmentManager().popBackStack();
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