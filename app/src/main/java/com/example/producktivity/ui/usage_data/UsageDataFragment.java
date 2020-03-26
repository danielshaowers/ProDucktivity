package com.example.producktivity.ui.usage_data;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;

import java.util.List;

public class UsageDataFragment extends Fragment {

    private DataViewModel dataViewModel;
    private List<UsageDataHandler.UsageTime> allData;



    @RequiresApi(api = Build.VERSION_CODES.N)

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.context = getContext();
        System.out.println("creating a tracker");
        UsageDataHandler handler = new UsageDataHandler(this.getContext());
        allData = handler.getStats();
        dataViewModel.setAllData(allData);
        View root = inflater.inflate(R.layout.usage_data, container, false);
        //this line watches the data view model for any changes, adjusting accordingly
        dataViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<UsageDataHandler.UsageTime>>() {
            @Override
            public void onChanged(@Nullable List<UsageDataHandler.UsageTime> s) {
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.app_recyclerView);
        final appAdapter adapter = new appAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }


    public void createGraph(){

    }
}