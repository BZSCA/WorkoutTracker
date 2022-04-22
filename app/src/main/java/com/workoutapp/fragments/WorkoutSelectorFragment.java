package com.workoutapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.dataclasses.ExerciseData;
import com.workoutapp.dataclasses.Routine;
import com.workoutapp.dataclasses.Workout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutSelectorFragment extends Fragment implements WorkoutSelectorAdapter.ItemClickListener {

    private static final String ARG_PARAM2 = "routine";
    private WorkoutSelectorAdapter adapter;
    private Routine routine;
    private WorkoutSelectorFragment.Interface mainActivity;

    public WorkoutSelectorFragment(WorkoutSelectorFragment.Interface mainActivity) {
        this.mainActivity = mainActivity;
        // Required empty public constructor
    }

    public static WorkoutSelectorFragment newInstance(Routine routine, WorkoutSelectorFragment.Interface mainActivity) {
        WorkoutSelectorFragment fragment = new WorkoutSelectorFragment(mainActivity);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, routine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            routine = (Routine) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_selector, container, false);

        EditText routineName = view.findViewById(R.id.routine);
        routineName.setText(routine.getName());

        routineName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    routine.setName(String.valueOf(routineName.getText()));
                    saveRoutine(routine);
                }
                return true;
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.rvWorkoutSelector);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
//            @Override
//            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
//                return false;
//            }
//        });

        view.findViewById(R.id.addWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routine.getWorkouts().add(new Workout("", DayOfWeek.MONDAY, new ArrayList<ExerciseData>()));
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
        });

        view.findViewById(R.id.removeWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routine.getWorkouts().remove(routine.getWorkouts().size() - 1);
                adapter.notifyItemRemoved(adapter.getItemCount());
            }
        });

        Log.i("routine workout length", String.valueOf(routine.getWorkouts().size()));
        adapter = new WorkoutSelectorAdapter(getContext(), routine);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        Log.i("adapter item length", String.valueOf(adapter.getItemCount()));

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        replaceFragment(WorkoutEditorFragment.newInstance(routine, position));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public void saveRoutine(Routine routine){
        mainActivity.buildToolbar(routine);
        Routine.saveRoutine(routine);
    }

    public interface Interface {
        void buildToolbar(Routine routine);
    }
}