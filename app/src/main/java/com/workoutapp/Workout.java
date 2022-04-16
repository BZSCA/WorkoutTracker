package com.workoutapp;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises;
    private ArrayList<ExerciseData> exerciseData;

    public Workout(String name, ArrayList<ExerciseData> exerciseData){
        this.name = name;
        this.exercises = new ArrayList<>();
        for (ExerciseData e : exerciseData){
            this.exercises.add(new Exercise(e));
        }
    }

    public ArrayList<ExerciseData> getExerciseData() { return this.exerciseData; }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public ArrayList<Exercise> getExercises(){
        return this.exercises;
    }

    public int size() {
        return exercises.size();
    }

    public void saveSelf() {

        File todoFile = new File(MyApp.getContext().getDataDir(), "workout.tmp");
        try {
            FileOutputStream fileOut = new FileOutputStream(todoFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e("Save exception", e.toString());
        }
    }

}
