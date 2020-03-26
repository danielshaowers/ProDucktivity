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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;

import java.util.List;

public class UsageDataFragment extends Fragment {

    private DataViewModel dataViewModel;
    private List<UsageTime> allData;



    @RequiresApi(api = Build.VERSION_CODES.N)

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        System.out.println("creating a tracker");
        UsageDataHandler handler = new UsageDataHandler(this.getContext());
        allData = handler.getStats();
        dataViewModel.setAllData(allData); //passes to the viewmodel
        View root = inflater.inflate(R.layout.usage_data, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.app_recyclerView);
        final AppAdapter adapter = new AppAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        //this line watches the data view model for any changes, adjusting accordingly
        dataViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<UsageTime>>() {
            @Override
            public void onChanged(@Nullable List<UsageTime> s) {
                adapter.setData(s);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }


    public void createGraph(){

    }
}