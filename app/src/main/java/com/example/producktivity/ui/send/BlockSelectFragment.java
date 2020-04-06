package com.example.producktivity.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.TempBlackListEntry;
import com.example.producktivity.ui.usage_data.AppAdapter;
import com.example.producktivity.ui.usage_data.DataViewModel;
import com.example.producktivity.ui.usage_data.UsageDataHandler;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectFragment extends Fragment {

    private BlockSelectViewModel blockSelectViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blockSelectViewModel =
                ViewModelProviders.of(this).get(BlockSelectViewModel.class);
        UsageDataHandler handler = new UsageDataHandler(this.getContext());
        blockSelectViewModel.setSelectList(blockSelectViewModel.initializeList(handler.getStats())); //passes to the viewmodel
        View root = inflater.inflate(R.layout.block_select_rcycler, container, false);
        List<TempBlackListEntry> selectList = new ArrayList<TempBlackListEntry>();
        //observes if there are any modifications to the select list and automatically performs onChanged
        RecyclerView rView = root.findViewById(R.id.select_recycler);
        final SelectAdapter adapter = new SelectAdapter(this.getContext());
        rView.setAdapter(adapter);
        blockSelectViewModel.getSelectList().observe(getViewLifecycleOwner(), new Observer<List<TempBlackListEntry>>() {
            @Override
            public void onChanged(@Nullable List<TempBlackListEntry> s) {
                adapter.setLimitList(s);
                adapter.notifyDataSetChanged();
                //I might also want to change the actual values within the recycler view, but to do that I need to inflate the single_select_recycler
            }
        });
        return root;
    }
}