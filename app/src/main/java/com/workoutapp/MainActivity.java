package com.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.workoutapp.dataclasses.ExerciseData;
import com.workoutapp.dataclasses.Routine;
import com.workoutapp.dataclasses.Workout;
import com.workoutapp.fragments.WorkoutEditorFragment;
import com.workoutapp.fragments.WorkoutFragment;
import com.workoutapp.fragments.WorkoutSelectorFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.time.DayOfWeek;

public class MainActivity extends AppCompatActivity implements Routine.Interface{


    private ArrayList<WorkoutFragment> workoutFragments;
    private ArrayList<PrimaryDrawerItem> drawerWorkouts;
    private Drawer result;
    private TextView toolbarTitle;
    private WorkoutSelectorFragment workoutSelectorFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        createDirectories();

        setContentView(R.layout.activity_main);
        toolbarTitle = findViewById(R.id.toolbarTitle);

        Routine routine = new Routine("TestRoutine", this);

        ArrayList<ExerciseData> exercises = new ArrayList<>();

        exercises.add(new ExerciseData("dips", 5.0, 4, 10, 100));
        exercises.add(new ExerciseData("curls", 20.0, 3, 12, 60));
        routine.getWorkouts().add(new Workout("Arms?", DayOfWeek.FRIDAY, new ArrayList<>(exercises)));

        exercises.clear();
        exercises.add(new ExerciseData("squats", 5.0, 4, 10, 100));
        exercises.add(new ExerciseData("lunges", 20.0, 3, 12, 60));
        routine.getWorkouts().add(new Workout("Legs", DayOfWeek.TUESDAY, new ArrayList<>(exercises)));

        workoutFragments = new ArrayList<>();

        for (Workout w : routine.getWorkouts()) {
            workoutFragments.add(WorkoutFragment.newInstance(w));
        }

        workoutSelectorFragment = workoutSelectorFragment.newInstance(routine);

        replaceFragment(workoutFragments.get(0));

        buildToolbar(routine);


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();

    }

    private boolean readItems() {
        File todoFile = new File(MyApp.getContext().getDataDir(), "workout.tmp");
        try {
            FileInputStream fileIn = new FileInputStream(todoFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Workout workout = (Workout) in.readObject();
            in.close();
            fileIn.close();
            return true;
        } catch (IOException | ClassNotFoundException i) {
            Log.e("failed to read", i.toString());
            return false;
        }
    }

    @Override
    public void buildToolbar(Routine routine) {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerBuilder drawerBuilder = new DrawerBuilder().withActivity(this).withToolbar(toolbar);

        for (int i = 0; i < routine.getWorkouts().size(); i++) {
            //drawerWorkouts.add(new PrimaryDrawerItem().withIdentifier(1).withName("Routine Selector"));
            drawerBuilder.addDrawerItems(new SecondaryDrawerItem().withIdentifier(i).withName(routine.getWorkouts().get(i).getName()));
        }

        drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withIdentifier((long) -3).withName("Routine Selector"),
                new PrimaryDrawerItem().withIdentifier((long) -4).withName("Workout Editor"),
                new PrimaryDrawerItem().withIdentifier((long) -5).withName("Workout History Viewer"));

        result = drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null) {
                    int id = Long.valueOf(drawerItem.getIdentifier()).intValue();
                    Log.i("replaceFragment", String.valueOf(id));
                    switch (id) {
                        case -3:
                            Log.i("replaceFragment", "Routine Selector");
                            result.closeDrawer();
                            break;
                        case -4:
                            replaceFragment(workoutSelectorFragment);
                            toolbarTitle.setText("Workout Selector");
                            result.closeDrawer();
                            break;
                        case -5:
                            //replaceFragment();
                            toolbarTitle.setText("Workout History");
                            result.closeDrawer();
                            break;
                        default:
                            replaceFragment(workoutFragments.get((int) id));
                            toolbarTitle.setText(routine.getWorkouts().get((int) id).getName());
                            result.closeDrawer();
                    }
                    return true;
                }
                return false;
            }
        }).build();
    }

    private void createDirectories(){
        File routines = new File(MyApp.getContext().getDataDir(), "/Routines");
        if (routines.isDirectory()){
            //loop through and load routines
        }
        else{
            routines.mkdir();
        }

        File workoutHistory = new File(MyApp.getContext().getDataDir(), "/Workout History");
        if (!workoutHistory.isDirectory()){
            workoutHistory.mkdir();
        }
    }

}