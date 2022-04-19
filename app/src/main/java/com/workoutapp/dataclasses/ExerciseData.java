package com.workoutapp.dataclasses;

import java.io.Serializable;

public class ExerciseData implements Serializable {

    public ExerciseData() {
        this("", 10.0, 3, 10, 60);
    }

    public ExerciseData(String name, double weight, double sets, int reps, int rest) {
        this.name = name;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getSets() {
        return sets;
    }

    public void setSets(double sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    private double weight;
    private double sets;
    private int reps;
    private int rest;

}
