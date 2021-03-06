package com.example.producktivity.ui.usage_data;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.ui.blockSelect.BlockSelectFragment;
import com.example.producktivity.ui.blockSelect.BlockSelectViewModel;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UsageDataFragment extends Fragment {

    private BlockSelectViewModel bsViewModel;
    private AppAdapter adapter;
    private UsageDataHandler handler;
    private Spinner dropdown;
    private int span;
    private  BarGraphSeries<com.jjoe64.graphview.series.DataPoint> barSeries;


    @RequiresApi(api = Build.VERSION_CODES.N)

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        handler = new UsageDataHandler(this.getContext());

        View root = inflater.inflate(R.layout.usage_data, container, false);
        dropdown = root.findViewById(R.id.change_span); //the drop down
        System.out.println(dropdown + "sort by dropdown");
        //allData = handler.getStats(UsageTime.WEEK); //default
       // dataViewModel.setAllData(allData); //passes to the viewmodel
        bsViewModel =  ViewModelProviders.of(this.getActivity()).get(BlockSelectViewModel.class);
        RecyclerView recyclerView = root.findViewById(R.id.app_recyclerView);
        adapter = new AppAdapter(this.getContext());

        recyclerView.setAdapter(adapter);
        String[] items = new String[]{"Day", "Week", "Month"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(sortAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    // barSeries.resetData(vv);
                   adapter.setData(BlockSelectFragment.changeSpan(UsageTime.DAY, adapter.getData()));
                   span = UsageTime.DAY;
                }
                if (position == 1){
                    adapter.setData(BlockSelectFragment.changeSpan(UsageTime.WEEK, adapter.getData()));
                    span = UsageTime.WEEK;
                }
                if (position == 2){ //does this notify the observer? what does the observer even observe??
                    adapter.setData(BlockSelectFragment.changeSpan(UsageTime.MONTH, adapter.getData()));
                    span = UsageTime.MONTH;
                }
                if (barSeries != null && adapter.getData() != null)
                    barSeries.resetData(generatePoints(adapter.getData(), 5));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //when the selection disappears from view, like if they click out of the spinner without selecting anything
            }
        });
        //this line watches the data view model for any changes, adjusting accordingly

       // dataViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<UsageTime>>() {
          bsViewModel.getSelectList().observe(getViewLifecycleOwner(), new Observer<List<BlacklistEntry>>() {
              @Override
              public void onChanged(List<BlacklistEntry> s) {
                  if (s == null || s.size() == 0){
                      if (s == null || s.size() == 0){
                          System.out.println("creating a new list");
                          Object selected = dropdown.getSelectedItem();
                          int timeFrame = selected == null ? UsageTime.MONTH: selected.equals("Day") ? UsageTime.DAY : (selected.equals("Month") ? UsageTime.MONTH:UsageTime.WEEK);
                          List<UsageTime> usagetimes = handler.getStats(timeFrame);
                          bsViewModel.updateList(usagetimes, s);
                          System.out.println(usagetimes.size() + " usage time size");
                      }
                  }

                  adapter.setData(s);
                  GraphView graphView = root.findViewById(R.id.graph);
                  if (adapter.getData() != null && adapter.getData().size() > 0)
                  createGraph(graphView, adapter.getData());
          }});
        /*GraphView graphView = root.findViewById(R.id.graph);
        createGraph(graphView);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }
    //changes the flag of everything in the list (sort them again too or nah?
   public static List<UsageTime> changeSpan(int time_flag, List<UsageTime> list){
        for (UsageTime u : list){
            u.span_flag = time_flag;
        }
        return list;
   }
    @Override
    public void onPause(){
        super.onPause();
       /* Object selected = dropdown.getSelectedItem();
        int timeFrame = selected == null ? UsageTime.MONTH: selected.equals("Day") ? UsageTime.DAY : (selected.equals("Month") ? UsageTime.MONTH:UsageTime.WEEK);
        bsViewModel.updateList(handler.getStats(timeFrame), adapter.getData());
        //todo: probably want to move this to the main activity, or just add an "update" button.
        //todo: add daily average calculator, should be very easy just divide by 24 hours
       /* Object selected = dropdown.getSelectedItem();
        int timeFrame = selected == null ? UsageTime.MONTH: selected.equals("Day") ? UsageTime.DAY : (selected.equals("Month") ? UsageTime.MONTH:UsageTime.WEEK);
        bsViewModel.updateList(handler.getStats(timeFrame), adapter.getData()); */
    }

    public DataPoint[] generatePoints (List<BlacklistEntry> list, int size){
        size = Math.min(size, list.size());
        DataPoint[] output = new DataPoint[size];
        AtomicInteger n = new AtomicInteger(0); //For incrementing in a stream
        List<DataPoint> dataPoints = list.stream().limit(size)
                .map(d -> new DataPoint(n.getAndIncrement(), d.getTimeOfFlag(d.getSpan_flag())/60000))
                .collect(Collectors.toList());
        int i = 0;
        for (DataPoint d : dataPoints)
            output[i++] = d;
        return output;
    }

    public void createGraph(GraphView graph, List<BlacklistEntry> list){
        graph.removeAllSeries();
        int size = Math.min(list.size(), 6);
        //Fill the series with the data and add it to the graph
        DataPoint[] dps = generatePoints(list, size);
        Log.i("graph", "list size is " + dps.length);


        barSeries = new BarGraphSeries<com.jjoe64.graphview.series.DataPoint>(dps);

        barSeries.setDataWidth(10);
        barSeries.setDrawValuesOnTop(true);
        barSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        barSeries.setSpacing(100);
        barSeries.setValuesOnTopColor(Color.RED);
        //barSeries.se
        graph.addSeries(barSeries);
        GridLabelRenderer format = graph.getGridLabelRenderer();
        format.resetStyles();
        format.setVerticalAxisTitle("Minutes");
        format.setHorizontalAxisTitle("Apps");
        /*format.setLabelsSpace(100);
        format.setPadding(10);*/

        graph.getGridLabelRenderer().setHumanRounding(false, true);
        graph.getGridLabelRenderer().setNumHorizontalLabels(dps.length);
        format.setHorizontalLabelsAngle(60);
        format.setTextSize(30);
        // format.setLabelHorizontalHeight(50);

        //format.draw();
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        graph.getViewport().setScalable(false); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(false); // enables vertical zooming and scrolling
        //Change graph format to show the name of the app
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    return bsViewModel.getSelectList().getValue().get((int)value).getAppName();
                } else {
                    return super.formatLabel(value, false);
                }
            }
        });
    }
}

