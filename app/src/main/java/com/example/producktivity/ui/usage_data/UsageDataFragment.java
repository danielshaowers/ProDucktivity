package com.example.producktivity.ui.usage_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;

public class UsageDataFragment extends Fragment {

    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        System.out.println("creating a tracker");
        UsageDataHandler handler = new UsageDataHandler(getContext());
        handler.getStats();
        View root = inflater.inflate(R.layout.usage_data, container, false);
        final TextView textView = root.findViewById(R.id.title);
        //this line watches the data view model for any changes, adjusting accordingly
        dataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}