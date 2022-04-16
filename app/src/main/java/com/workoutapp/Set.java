package com.workoutapp;

import java.io.Serializable;

public class Set implements Serializable {
    private double weight;
    private int reps;

    public Set(double weight, int reps){
        this.weight = weight;
        this.reps = reps;
    }

    public boolean getState() { return this.weight >= 0 && this.reps > 0;}

    public double getWeight(){
        return this.weight;
    }

    public void setWeight(double number){ this.weight = number; }

    public int getReps(){ return this.reps; }

    public void setReps(int i) { this.reps = i; }

}
