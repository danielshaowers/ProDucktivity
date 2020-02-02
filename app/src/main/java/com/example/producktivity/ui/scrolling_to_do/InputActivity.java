package com.example.producktivity.ui.scrolling_to_do;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.IDNA;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

//implements InputAdapter.ItemClickListener if i want it to register button clicks
public class InputActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InputAdapter mAdapter;
    private Combine dataCombined;
    // private ArrayList<String> data = new ArrayList<>();
    private ArrayList<Info> data = new ArrayList<>();
    public static final int GET_FROM_GALLERY = 1;

    //  private ArrayList<Data> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);
        // use a constraint layout manager
        recyclerView = (RecyclerView) findViewById(R.id.todo_recyclerview);
        //  recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.GRID));
        data.add(new Info(""));
        data.add(new Info(""));
        mAdapter = new InputAdapter(data);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); ////i may want to do constraintlayout, in which case i might have to make a separate class that extends recyclerview.layoutmanager
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
     /*   ImageButton buttonLoadImage = findViewById(R.id.photoButton);
        buttonLoadImage.setOnClickListener(new Set1.AddImageListener()); */
/*
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
    public void studyNow(View view){
        Intent parcelIntent = new Intent(this, Calendar.class);
        parcelIntent.putParcelableArrayListExtra("success", data);
        startActivity(parcelIntent);
    }

    //when the user is done with the gallery and returns, this method is called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }
}
