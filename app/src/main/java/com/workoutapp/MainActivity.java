package com.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity  {

    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ArrayList<ExerciseData> exercises = new ArrayList<>();

        exercises.add(new ExerciseData("dips", 5.0, 4, 10, 100 ));
        exercises.add(new ExerciseData("curls", 20.0, 3, 12, 60 ));


        workout = new Workout("Test", exercises);

        Log.i("set size", String.valueOf(workout.getExercises().get(0).size()));

        Fragment workoutFragment = WorkoutFragment.newInstance(workout);

        replaceFragment(workoutFragment);

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();

    }

    private boolean readItems() {
        File todoFile = new File(MyApp.getContext().getDataDir(), "workout.tmp");
        try {
            FileInputStream fileIn = new FileInputStream(todoFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            workout = (Workout) in.readObject();
            in.close();
            fileIn.close();
            return true;
        } catch (IOException | ClassNotFoundException i) {
            Log.e("failed to read", i.toString());
            return false;
        }
    }

}