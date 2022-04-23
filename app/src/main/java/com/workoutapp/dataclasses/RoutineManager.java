package com.workoutapp.dataclasses;

import android.util.Log;

import com.workoutapp.MyApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class RoutineManager {

    private ArrayList<Routine> routines;
    private Routine currentRoutine;

    public RoutineManager(){
        loadRoutines();

        Routine routine = new Routine("TestRoutine");

        ArrayList<ExerciseData> exercises = new ArrayList<>();

        exercises.add(new ExerciseData("dips", 5.0, 4, 10, 100));
        exercises.add(new ExerciseData("curls", 20.0, 3, 12, 60));
        routine.getWorkouts().add(new Workout("Arms?", DayOfWeek.FRIDAY, new ArrayList<>(exercises)));

        exercises.clear();
        exercises.add(new ExerciseData("squats", 5.0, 4, 10, 100));
        exercises.add(new ExerciseData("lunges", 20.0, 3, 12, 60));
        routine.getWorkouts().add(new Workout("Legs", DayOfWeek.TUESDAY, new ArrayList<>(exercises)));

        routines.add(routine);
        saveRoutines();

        setCurrentRoutine(0);
    }

    public List<Routine> getRoutines(){
        return this.routines;
    }

    public void setCurrentRoutine(int position){
        this.currentRoutine = routines.get(position);
    }

    public Routine getCurrentRoutine(){
        return this.currentRoutine;
    }

    public void addRoutine(Routine routine){
        routines.add(routine);
        saveRoutines();
    }

    public void saveRoutines(){
        File file = new File(MyApp.getContext().getDataDir(), "/Routines.rtn");
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(routines);
            out.close();
            fileOut.close();
            Log.i("Routine Manager", "Saved Routines successfully");
        } catch (IOException e) {
            Log.e("Error saving routines exception", e.toString());
        }

    }

    public void loadRoutines(){
        File file = new File(MyApp.getContext().getDataDir(), "/Routines.rtn");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            // Method for deserialization of object
            this.routines = (ArrayList<Routine>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            this.routines = new ArrayList<Routine>();
        }
    }

}
