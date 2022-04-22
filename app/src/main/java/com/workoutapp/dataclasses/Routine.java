package com.workoutapp.dataclasses;

import android.app.Activity;
import android.util.Log;

import com.workoutapp.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Routine implements Serializable {

    String name;
    ArrayList<Workout> workouts;

    public Routine(String name) {
        this.name = name;
        this.workouts = new ArrayList<>();
    }

    public Routine(String name, ArrayList<Workout> workouts) {
        this.name = name;
        this.workouts = workouts;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    public void setName(String string) {
        this.name = string;;
    }

    public static void saveRoutine(Routine routine){
        File file = new File(MyApp.getContext().getDataDir(), "/Routines/" +  routine.getName() +  ".rtn");
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(routine);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e("Error saving routine exception", e.toString());
        }

    }

}
