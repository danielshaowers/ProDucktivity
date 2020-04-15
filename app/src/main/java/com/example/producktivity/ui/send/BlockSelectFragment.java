package com.example.producktivity.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;

import com.example.producktivity.dbs.blacklist.Category;
import com.example.producktivity.dbs.blacklist.CategoryConverter;
import com.example.producktivity.ui.usage_data.AppAdapter;
import com.example.producktivity.ui.usage_data.DataViewModel;
import com.example.producktivity.ui.usage_data.UsageDataFragment;

import com.example.producktivity.ui.usage_data.UsageDataHandler;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectFragment extends Fragment {

    private BlockSelectViewModel blockSelectViewModel;
    private SelectAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blockSelectViewModel =
                ViewModelProviders.of(this.getActivity()).get(BlockSelectViewModel.class);
        UsageDataHandler handler = new UsageDataHandler(this.getContext());
        View root = inflater.inflate(R.layout.block_select_rcycler, container, false);
        Spinner spintowin = root.findViewById(R.id.choose_cat);
        String[] items = new String[]{ "ART_AND_DESIGN", "AUGMENTED_REALITY", "AUTO_AND_VEHICLES", "BEAUTY", "BOOKS_AND_REFERENCE", "BUSINESS",
                "COMICS", "COMMUNICATION", "DATING", "EDUCATION", "ENTERTAINMENT", "EVENTS", "FAMILY", "FINANCE", "FOOD_AND_DRINK",
                "GAMES", "GOOGLE_CAST", "HEALTH_AND_FITNESS", "HOUSE_AND_HOME", "LIBRARIES_AND_DEMO", "LIFESTYLE",
                "MAPS_AND_NAVIGATION", "MEDICAL"};

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spintowin.setAdapter(sortAdapter);
        final Category[] selected = new Category[1]; //category chosen in dropdown
        Button save_cat = root.findViewById(R.id.save_cat); //todo: override onclick to update all in list
        TextView dayLim = root.findViewById(R.id.category_limit);
        TextView weekLim = root.findViewById(R.id.category_limit_week);
        save_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = dayLim.getText().toString();
                String week = weekLim.getText().toString();
                List<BlacklistEntry> list = adapter.getLimits();
                for(BlacklistEntry e : list) { //technically, viewmodel should notice and notify adapter for us
                      if (e.getCategory() == selected[0]) {
                         long dayL = BlacklistEntry.stringToLong(day);
                         long weekL = BlacklistEntry.stringToLong(week);
                         if (dayL > 0)
                             e.setDayLimit(dayL);
                         if (weekL > 0)
                             e.setWeekLimit(weekL);
                       }
                   }
                adapter.setLimitList(list); //this re-sorts everything
            }
        });
        //make sure you only update the day/week limits when something is actually entered
        //it may help to create a dummy blacklist entry just so you have access to some of the methods with converting strings to time (though they might be static methods, i forget)

        //todo: update the daily and hourly limit of all apps in this category, when the save button is pressed
        spintowin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(items[position] + " selected");
                selected[0] = Category.valueOf(items[position]);
                /*List<BlacklistEntry> list = adapter.getLimits();
                   for(BlacklistEntry e : list) { //technically, viewmodel should notice and notify adapter for us
                       if (e.getCategory().toString().equals(items[position]))
                           e.set
                   }*/
            }
            //todo: make sure the viewmodel observer genuinely updates with any changes made
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //when the selection disappears from view, like if they click out of the spinner without selecting anything
            }
        });

        //observes if there are any modifications to the select list and automatically performs onChanged
        RecyclerView rView = root.findViewById(R.id.select_recycler);
        adapter = new SelectAdapter(this.getContext());
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        blockSelectViewModel.getSelectList().observe(getViewLifecycleOwner(), new Observer<List<BlacklistEntry>>() {
            @Override
            public void onChanged(@Nullable List<BlacklistEntry> s) {
                System.out.println("onChanged called for the blockselect viewmodel");
                if (s == null || s.size() == 0){
                    System.out.println("creating a new list");
                    Object selected = spintowin.getSelectedItem();
                    int timeFrame = selected == null ? UsageTime.MONTH: selected.equals("Day") ? UsageTime.DAY : (selected.equals("Month") ? UsageTime.MONTH:UsageTime.WEEK);
                    List<UsageTime> usagetimes = handler.getStats(timeFrame);
                    blockSelectViewModel.updateList(usagetimes, s);
                    System.out.println(usagetimes.size() + " usage time size");
                }
                adapter.setLimitList(s);
                //I might also want to change the actual values within the recycler view, but to do that I need to inflate the single_select_recycler
            }
        });
        return root;
    }


    //called when the fragment is no longer in view
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("saving the following");
        for (BlacklistEntry e: adapter.getLimits())
            System.out.println(e.getAppName() + "is unrestricted? " + e.isUnrestricted());
        blockSelectViewModel.replaceDB(adapter.getLimits());
    }

    //when the flag is changed
    public static List<BlacklistEntry> changeSpan(int time_flag, List<BlacklistEntry> list){
        if (list != null)
            for (BlacklistEntry u : list){
                u.setSpan_flag(time_flag);
            }
        return list;
    }

}