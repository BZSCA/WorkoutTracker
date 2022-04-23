package com.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.workoutapp.dataclasses.RoutineManager;
import com.workoutapp.dataclasses.Workout;
import com.workoutapp.fragments.RoutineSelectorAdapter;
import com.workoutapp.fragments.RoutineSelectorFragment;
import com.workoutapp.fragments.WorkoutEditorFragment;
import com.workoutapp.fragments.WorkoutFragment;
import com.workoutapp.fragments.WorkoutSelectorFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private ArrayList<WorkoutFragment> workoutFragments;
    private Drawer result;
    private TextView toolbarTitle;
    private WorkoutSelectorFragment workoutSelectorFragment;
    private RoutineSelectorFragment routineSelectorFragment;
    private RoutineManager routineManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        createDirectories();

        routineManager = new RoutineManager();

        setContentView(R.layout.activity_main);
        toolbarTitle = findViewById(R.id.toolbarTitle);

        workoutFragments = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("u");
        Date date = new Date();
        Log.i("date", formatter.format(date));
        int dayOfWeek = Integer.parseInt(formatter.format(date));

        int workoutNum = 0;

        ArrayList<Workout> workouts = routineManager.getCurrentRoutine().getWorkouts();

        for (int i = 0; i < workouts.size(); i++){
            workoutFragments.add(WorkoutFragment.newInstance(workouts.get(i)));
            if( workouts.get(i).getDayOfWeek().ordinal() == dayOfWeek){
                workoutNum = i;
            }
        }

        for (Workout w : routineManager.getCurrentRoutine().getWorkouts()) {
            workoutFragments.add(WorkoutFragment.newInstance(w));

        }

        workoutSelectorFragment = workoutSelectorFragment.newInstance(routineManager);
        routineSelectorFragment = routineSelectorFragment.newInstance(routineManager);

        replaceFragment(workoutFragments.get(workoutNum));

        buildToolbar(routineManager.getCurrentRoutine());

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
                new PrimaryDrawerItem().withIdentifier((long) -4).withName("Routine Editor"),
                new PrimaryDrawerItem().withIdentifier((long) -5).withName("Workout History Viewer"));

        result = drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null) {
                    int id = Long.valueOf(drawerItem.getIdentifier()).intValue();
                    Log.i("replaceFragment", String.valueOf(id));
                    switch (id) {
                        case -3:
                            replaceFragment(routineSelectorFragment);
                            toolbarTitle.setText("Routine Selector");
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