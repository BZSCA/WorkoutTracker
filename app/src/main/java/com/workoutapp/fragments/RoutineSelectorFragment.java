package com.workoutapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.dataclasses.Routine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutineSelectorFragment extends Fragment implements RoutineSelectorAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    RoutineSelectorAdapter adapter;

    private String mParam1;

    public RoutineSelectorFragment() {
        // Required empty public constructor
    }

    public static RoutineSelectorFragment newInstance() {
        RoutineSelectorFragment fragment = new RoutineSelectorFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_selector, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvRoutineSelector);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
        adapter = new RoutineSelectorAdapter(MyApp.getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.addRoutine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindowClick(view);
            }
        });

        return view;
    }

    public void showPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.routine_namer_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);



        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText editText = popupView.findViewById(R.id.newRoutineName);
        editText.setText("");

        popupView.findViewById(R.id.popupAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String routineName = String.valueOf(editText.getText());

                if (routineName.isEmpty()){
                    Toast.makeText(MyApp.getContext(), "Routine name cannot be empty", Toast.LENGTH_SHORT).show();
                    Log.i("Toast", "Routine name cannot be empty");
                }
                else{
                    File f = new File(MyApp.getContext().getDataDir(), "/Routines/" +  routineName +  ".rtn");
                    if(f.exists() && !f.isDirectory()){
                        Toast.makeText(MyApp.getContext(), "Routine name cannot be a duplicate", Toast.LENGTH_SHORT).show();
                        Log.i("Toast", "Routine name cannot be a duplicate");
                    } else{
                        Routine.saveRoutine(new Routine(routineName));
                        popupWindow.dismiss();
                        adapter.getRoutines();

                    }
                }
            }
        });

        popupView.findViewById(R.id.popupCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        //sharedPreferences.edit().putString("saved routine");
    }
}