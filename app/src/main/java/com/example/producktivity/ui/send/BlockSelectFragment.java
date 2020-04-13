package com.example.producktivity.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.BlacklistEntry;
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
                ViewModelProviders.of(this).get(BlockSelectViewModel.class);
        UsageDataHandler handler = new UsageDataHandler(this.getContext());
        View root = inflater.inflate(R.layout.block_select_rcycler, container, false);
        Spinner spintowin = root.findViewById(R.id.sort_blocked);
        Object selected = spintowin.getSelectedItem();
        int timeFrame = selected == null ? UsageTime.MONTH: selected.equals("Day") ? UsageTime.DAY : (selected.equals("Month") ? UsageTime.MONTH:UsageTime.WEEK);
        List<UsageTime> usagetimes = handler.getStats(timeFrame);
        String[] items = new String[]{"Day", "Week", "Month"};
        final List<BlacklistEntry>[] selectList = new List[]{blockSelectViewModel.getSelectList().getValue()};
        if (selectList[0] == null || selectList[0].size() == 0) //if it's currently empty, then we create the list from scratch
            selectList[0] = blockSelectViewModel.initializeList(usagetimes);
        else{ //updates existing database with new usage times
            blockSelectViewModel.updateList(usagetimes, selectList[0]);
        }
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items); //todo: look this might be null ahhh
        spintowin.setAdapter(sortAdapter);

        spintowin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    //adapter.setData(UsageDataFragment.changeSpan(UsageTime.DAY, allData));
                    //adapter.notifyDataSetChanged();
                    selectList[0] = BlockSelectFragment.changeSpan(UsageTime.DAY, selectList[0]); //technically, viewmodel should notice and notify adapter for us
                }
                if (position == 1){
                    selectList[0] = BlockSelectFragment.changeSpan(UsageTime.WEEK, selectList[0]); //technically, viewmodel should notice and notify adapter for us
                    //adapter.setData(UsageDataFragment.changeSpan(UsageTime.DAY, allData));
                    //adapter.notifyDataSetChanged();
                }
                if (position == 2){
                    //adapter.setData(UsageDataFragment.changeSpan(UsageTime.DAY, allData));
                    //adapter.notifyDataSetChanged();
                    selectList[0] = BlockSelectFragment.changeSpan(UsageTime.MONTH, selectList[0]); //technically, viewmodel should notice and notify adapter for us
                }
            }
            //todo: make sure the viewmodel observer genuinely updates with any changes made
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //when the selection disappears from view, like if they click out of the spinner without selecting anything
            }
        });
         blockSelectViewModel.setSelectList(selectList[0]); //todo: instead of initializing the list here, pull from database?
        //observes if there are any modifications to the select list and automatically performs onChanged
        RecyclerView rView = root.findViewById(R.id.select_recycler);
        adapter = new SelectAdapter(this.getContext());
        adapter.setLimitList(selectList[0]);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        blockSelectViewModel.getSelectList().observe(getViewLifecycleOwner(), new Observer<List<BlacklistEntry>>() {
            @Override
            public void onChanged(@Nullable List<BlacklistEntry> s) {
                adapter.setLimitList(s);
                adapter.notifyDataSetChanged();
                //I might also want to change the actual values within the recycler view, but to do that I need to inflate the single_select_recycler
            }
        });
        return root;
    }
    @Override
    public void onPause(){
        super.onPause();
        blockSelectViewModel.replaceDB(adapter.getLimits());
    }

    //when the flag is changed
    public static List<BlacklistEntry> changeSpan(int time_flag, List<BlacklistEntry> list){
        for (BlacklistEntry u : list){
            u.setSpan_flag(time_flag);
        }
        return list;
    }

}