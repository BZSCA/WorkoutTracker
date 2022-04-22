package com.workoutapp.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.workoutapp.MyApp;
import com.workoutapp.R;
import com.workoutapp.dataclasses.Routine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class RoutineSelectorAdapter extends RecyclerView.Adapter<RoutineSelectorAdapter.ViewHolder> {

    private List<Routine> routines;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RoutineSelectorAdapter(Context context) {
        getRoutines();
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rvitem_routine, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = routines.get(position).getName();
        holder.myTextView.setText(name);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return routines.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.routineName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getBindingAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void getRoutines(){
        ArrayList<Routine> routines = new ArrayList<>();
        File folder = new File(MyApp.getContext().getDataDir(), "/Routines");

        for (final File file : folder.listFiles()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileInputStream);

                // Method for deserialization of object
                routines.add( (Routine) in.readObject() );
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        this.routines = routines;
        notifyDataSetChanged();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
