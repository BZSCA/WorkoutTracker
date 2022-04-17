package com.workoutapp;

import java.util.ArrayList;

public class Routine {

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

    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }


}
