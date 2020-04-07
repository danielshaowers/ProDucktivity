package com.example.producktivity.ui.usage_data;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.BlacklistEntry;

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
        Spinner dropdown = root.findViewById(R.id.usage_span);
        String[] items = new String[]{"Day", "Week", "Month"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(sortAdapter);
        final long[] timespan = {BlacklistEntry.stringToLong("24:00")};
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    timespan[0] = BlacklistEntry.stringToLong("24:00");
                }
                if (position == 1){
                    timespan[0] = BlacklistEntry.stringToLong("168:00");
                }
                if (position == 2){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //this line watches the data view model for any changes, adjusting accordingly
        dataViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<UsageTime>>() {
            @Override //THIS IS REDUNDANT. SHOULD UPDATE DATABASE, NOT THIS
            public void onChanged(@Nullable List<UsageTime> s) {
                //Todo: update blacklist database
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }


    public void createGraph(){

    }
}