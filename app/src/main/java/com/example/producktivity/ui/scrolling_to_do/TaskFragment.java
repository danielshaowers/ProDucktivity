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
    public static final int GET_FROM_GALLERY = 1;
    private ToDoViewModel viewModel;

    //public View onCreateView()
   @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       //data.add(new Task());
       super.onCreate(savedInstanceState);
        //get access to the TaskViewModel class and its infinite wisdom/data
       // final TaskViewModel viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
       viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);
       //create the overall view specified by the to_do xml file
       View root = inflater.inflate(R.layout.to_do, container, false);
        //find the view we need to attach data from TaskViewModel to
       final EditText title = root.findViewById(R.id.todo_title);
       recyclerView = root.findViewById(R.id.todo_recyclerview); //did not declare it final
       final InputAdapter mAdapter = new InputAdapter(this.getContext());
       //we set the task list to observe+reflect any changes in text of TaskViewModel
       viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
           @Override
           public void onChanged(@Nullable List<Task> s) {
                   mAdapter.setTasks(s);
                   mAdapter.notifyDataSetChanged();
           }
       });
        //based on https://stackoverflow.com/questions/44489235/update-recyclerview-with-android-livedata

       //now if tasks ever changes, we notify the adapter that the dataset has changed, calling setDataSet
       //on the new list
       viewModel.getAllTasks().observe(getViewLifecycleOwner(), mAdapter::setTasks);
       recyclerView.setAdapter(mAdapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())); //not sure about this argument
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       return root;
    }
     /*   ImageButton buttonLoadImage = findViewById(R.id.photoButton);
        buttonLoadImage.setOnClickListener(new Set1.AddImageListener()); */
/* //IF I WANT TO ADD IMAGES
    public class AddImageListener implements ImageButton.OnClickListener{
        @Override
        public void onClick (View view){
        //starts an activity that opens the gallery
        //new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        //get the image and set the image
        view.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
    }
    }
    */
    /*IF I WANT TO SEND THIS INFO TO ANOTHER CLASS LIKE CALENDAR*/
   /* public void studyNow(View view){
        Intent parcelIntent = new Intent(this, Calendar.class);
        parcelIntent.putParcelableArrayListExtra("success", data);
        startActivity(parcelIntent);
    } */

    //when the user is done with the gallery and returns, this method is called
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {    //if we got an image from the gallery and the user didn't back out
            //get uri that points to the selected contact
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);    //the camera returns a bitmap, so here we store
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                Info current = this.data.get(mAdapter.getPos());
                //setImageDrawable(bitmapDrawable);
                //have to determine at which position it was clicked, get that imageView from the data arraylist, and set its imagedrawable
                current.setImage(bitmapDrawable);
                // current.setUri(current.getImageUri((Context) this, bitmap));
                current.setUri(selectedImage);
                mAdapter.notifyItemChanged(mAdapter.getPos()); //should i mess with payload? because it's specifically the image that changed
                //i need to do this one AFTER i convert the bitmap into an image.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } */
}
