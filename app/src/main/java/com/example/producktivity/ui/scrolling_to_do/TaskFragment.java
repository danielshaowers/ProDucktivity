package com.example.producktivity.ui.scrolling_to_do;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;
import com.example.producktivity.dbs.BlacklistEntry;
import com.example.producktivity.dbs.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//implements InputAdapter.ItemClickListener if i want it to register button clicks
//public class TaskFragment extends AppCompatActivity {
public class TaskFragment extends Fragment {
    private RecyclerView recyclerView;
    private InputAdapter mAdapter;
    private ArrayList<Task> data = new ArrayList<>();
    private ToDoViewModel viewModel;

    //public View onCreateView()
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        //get access to the TaskViewModel class and its infinite wisdom/data
       viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);
       final InputAdapter mAdapter = new InputAdapter(this.getContext());
       mAdapter.setTasks(viewModel.getAllTasks().getValue());
       //create the overall view specified by the to_do xml file.
       //the layout inflater converts a layout xml file into the Views that constitute it. Lets us interact with the xml contents
       View root = inflater.inflate(R.layout.to_do, container, false);
        //find the view we need to attach data from TaskViewModel to
       recyclerView = root.findViewById(R.id.todo_recyclerview);
       //we set the task list to observe+reflect any changes in text of TaskViewModel
       viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
           @Override
           public void onChanged(@Nullable List<Task> s) {
               //update database
               //I might also want to change the actual values within the recycler view, but to do that I need to inflate the single_select_recycler
            mAdapter.setTasks(s);
           }
       });
        //based on https://stackoverflow.com/questions/44489235/update-recyclerview-with-android-livedata

       //now if tasks ever changes, we notify the adapter that the dataset has changed, calling setDataSet
       //on the new list
       recyclerView.setAdapter(mAdapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())); //used to be root's context
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       return root;
    }
}
