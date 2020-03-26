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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

        GraphView graph = root.findViewById(R.id.graph);
        createGraph(graph);

        return root;
    }


    public void createGraph(GraphView graph){

        //Fill the series with the data and add it to the graph
        AtomicInteger n = new AtomicInteger(1);
        DataPoint[] dataPoints = (DataPoint[])allData.stream().limit(7).map(d -> new DataPoint(n.getAndIncrement(), d.millisUsed/60000)).toArray();
        BarGraphSeries<DataPoint> barSeries = new BarGraphSeries<>(dataPoints);
        graph.addSeries(barSeries);

        //Change graph format to show the name of the app
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    return super.formatLabel(value, true);
                } else {
                    return allData.get((int)value).appName;
                }
            }
        });
    }
}