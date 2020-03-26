package com.example.producktivity.ui.blocking;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;

public class BlockFragment extends Fragment {

    private BlockViewModel blockViewModel;

    private final int PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        blockViewModel = ViewModelProviders.of(this).get(BlockViewModel.class);
        System.out.println("creating a blocker");
        BlockerService blocker = new BlockerService(getContext());
        View root = inflater.inflate(R.layout.block_apps, container, false);
        final TextView textView = root.findViewById(R.id.title_block);
        blockViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        blocker.detect();
        return root;
    }

}