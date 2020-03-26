package com.example.producktivity.ui.blocking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;
import com.google.android.material.snackbar.Snackbar;

public class BlockActivity extends AppCompatActivity {

    private BlockViewModel blockViewModel;
    private int duckClicks = 0;

    private final int PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_page);

        blockViewModel = ViewModelProviders.of(this).get(BlockViewModel.class);
        System.out.println("creating a blocker");
      //  Blocker blocker = new Blocker(this);
      //  blocker.detect();
        final TextView textView = findViewById(R.id.title_block);
        ImageButton duck = findViewById(R.id.continueDuck);
        Button returnButt = findViewById(R.id.ReturnButton);
        returnButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //make this add a task in future
                Snackbar.make(view, "Good Choice!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
    });
    duck.setOnClickListener(new View.OnClickListener(){
       @Override
       public void onClick(View view){
           if (++duckClicks > 4){
                   Intent startMain = new Intent("com.example.producktivity.MainActivity");
                   startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(startMain);

           }
           else
               Snackbar.make(view, "Noooo don't do it!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
       }
    });
    }

}