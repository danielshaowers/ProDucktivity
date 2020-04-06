package com.example.producktivity.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.producktivity.R;
import com.example.producktivity.dbs.TempBlackListEntry;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<TempBlackListEntry>> list;
    public BlockSelectViewModel() {
        list = new MutableLiveData<>();

      /*  save = itemView.findViewById(R.id.save_select_button);
        appSelect = itemView.findViewById(R.id.app_select_button);
        dayText = itemView.findViewById(R.id.daily_limit_text);
        weekText = itemView.findViewById(R.id.weekly_limit_text);
        weekLimit = itemView.findViewById(R.id.weekly_limit);
        dayLimit = itemView.findViewById(R.id.daily_limit);
        card = itemView.findViewById(R.id.limit_card);*/
    }
    public void setSelectList(List<TempBlackListEntry> entry){
        list.setValue(entry);
    }
    public LiveData<List<TempBlackListEntry>> getSelectList() {
        return list;
    }
    public List<TempBlackListEntry> initializeList(List<UsageTime> usage){
        List<TempBlackListEntry> list = new ArrayList<TempBlackListEntry>();
        for (UsageTime u: usage){
            list.add(new TempBlackListEntry(u.appName));
        }
        return list;
    }
}