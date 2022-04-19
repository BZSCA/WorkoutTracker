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
    Routine.Interface routineInterface;

    public Routine(String name, Routine.Interface routineInterface) {
        this.name = name;
        this.workouts = new ArrayList<>();
        this.routineInterface = routineInterface;
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

    public void saveRoutine(){
        routineInterface.buildToolbar(this);
        File routine = new File(MyApp.getContext().getDataDir(), "/Routines" +  name +  ".rtn");
        try {
            FileOutputStream fileOut = new FileOutputStream(routine);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e("Save exception", e.toString());
        }
    }

    public void setName(String string) {
        this.name = string;;
    }

    public interface Interface{
        void buildToolbar(Routine routine);
    }

}
