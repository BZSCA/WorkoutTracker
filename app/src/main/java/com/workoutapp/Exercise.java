package com.workoutapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Exercise extends Observable implements Serializable {

    private int current;
    private int rest;
    private String name;
    private List<Set> sets;

    public Exercise(ExerciseData ed) {
        this.current = 0;
        this.rest = ed.getRest();
        this.name = ed.getName();
        this.sets = new ArrayList<>();
        for (int i = 0; i < ed.getSets(); i++) {
            this.sets.add(new Set(-ed.getWeight(), -ed.getReps()));
        }

    }

    public Set get(int i) {
        return sets.get(i);
    }

    public int size() {
        return sets.size();
    }

    public String getName() {
        return this.name;
    }

    public int getRest() {
        return this.rest;
    }

    public int getCurrent() {
        return this.current;
    }

    public void progressCurrent() {
        this.current += 1;
    }

    //define pos out of range of set???
    public boolean setWeight(int pos, double d) {
        if (d >= 0) {
            sets.get(pos).setWeight(d);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    public boolean setReps(int pos, int reps) {
        if (reps > 0) {
            sets.get(pos).setReps(reps);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
}
