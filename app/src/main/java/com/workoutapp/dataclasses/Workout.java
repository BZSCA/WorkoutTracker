package com.workoutapp.dataclasses;

import android.text.Editable;
import android.util.Log;

import com.workoutapp.MyApp;
import com.workoutapp.dataclasses.Exercise;
import com.workoutapp.dataclasses.ExerciseData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.DayOfWeek;

public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises;
    private ArrayList<ExerciseData> exerciseData;
    private DayOfWeek dayOfWeek;

    public Workout(String name, DayOfWeek dayOfWeek, ArrayList<ExerciseData> exerciseData) {
        this.name = name;
        this.exercises = new ArrayList<>();
        this.exerciseData = exerciseData;
        for (ExerciseData e : exerciseData) {
            this.exercises.add(new Exercise(e));
        }
        this.dayOfWeek = dayOfWeek;
    }

    public void updateWorkout() {
        exercises.clear();
        for (ExerciseData e : this.exerciseData) {
            this.exercises.add(new Exercise(e));
        }

    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public ArrayList<ExerciseData> getExerciseData() {
        return this.exerciseData;
    }

    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }

    public int size() {
        return exercises.size();
    }

    public String getName() {
        return this.name;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
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
            Log.e("Workout IOexception", e.toString());
        }
    }

    //consider using database implementation with ROOM or SQlite
    public void saveFinishedWorkout(){
        File finishedWorkout = new File(MyApp.getContext().getDataDir(), "/Workout History" + "/" + name + System.currentTimeMillis());
        try {
            FileOutputStream fileOut = new FileOutputStream(finishedWorkout);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e("Save exception", e.toString());
        }
    }

    public void setName(String string) {
        this.name = string;
    }
}
