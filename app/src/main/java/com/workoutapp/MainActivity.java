package com.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.time.DayOfWeek;

public class MainActivity extends AppCompatActivity {


    ArrayList<WorkoutFragment> workoutFragments;
    ArrayList<PrimaryDrawerItem> drawerWorkouts;
    Drawer result;
    TextView toolbarTitle;
    WorkoutEditorFragment workoutEditorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbarTitle = findViewById(R.id.toolbarTitle);

        ArrayList<ExerciseData> exercises = new ArrayList<>();

        exercises.add(new ExerciseData("dips", 5.0, 4, 10, 100));
        exercises.add(new ExerciseData("curls", 20.0, 3, 12, 60));


        Workout workout = new Workout("TestWorkout", DayOfWeek.FRIDAY, exercises);

        Log.i("set size", String.valueOf(workout.getExercises().get(0).size()));

        Routine routine = new Routine("TestRoutine");
        routine.addWorkout(workout);

        workoutFragments = new ArrayList<>();
        workoutFragments.add(WorkoutFragment.newInstance(workout));

        workoutEditorFragment = WorkoutEditorFragment.newInstance(workout);

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

    private void buildToolbar(Routine routine) {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerBuilder drawerBuilder = new DrawerBuilder().withActivity(this).withToolbar(toolbar);

        for (int i = 0; i < routine.getWorkouts().size(); i++) {
            //drawerWorkouts.add(new PrimaryDrawerItem().withIdentifier(1).withName("Routine Selector"));
            drawerBuilder.addDrawerItems(new SecondaryDrawerItem().withIdentifier(i).withName(routine.getWorkouts().get(i).getName() + " " + routine.getWorkouts().get(i).getDayOfWeek()));
        }

        drawerBuilder.addDrawerItems(
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withIdentifier((long) -3).withName("Routine Selector"),
                new PrimaryDrawerItem().withIdentifier((long) -4).withName("Workout Editor"));


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
                            replaceFragment(workoutEditorFragment);
                            toolbarTitle.setText("Workout Editor");
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

}