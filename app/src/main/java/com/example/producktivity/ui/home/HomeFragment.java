package com.example.producktivity.ui.home;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;
import com.example.producktivity.ui.blocking.BlockActivity;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.block_apps, container, false);
        Switch sw = root.findViewById(R.id.activate_blocker);
        homeViewModel.setOn(sw.isChecked());
        /*homeViewModel.getOn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                homeViewModel.setOn(s);
            }
        });*/
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //send to blockeractivity
                    ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> RunningTask = mActivityManager.getRunningAppProcesses();
                    // Get the info we need for comparison.
                    ComponentName componentInfo = RunningTask.get(0).importanceReasonComponent;
                    if (componentInfo == null || !componentInfo.getPackageName().equals(getActivity().getPackageName())) {
                        System.out.println("is this working");
                        Intent startBlock = new Intent(HomeFragment.this.getActivity(), BlockActivity.class);
                        startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(startBlock);
                    }
                }
            }}); return root;}}
        //} else {

        //}
    /*}
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}*/