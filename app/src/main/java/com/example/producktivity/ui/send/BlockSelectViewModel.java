package com.example.producktivity.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<BlacklistEntry>> list;
    public BlockSelectViewModel() {
        list = new MutableLiveData<>();
        //set the stats from the database.
        //right now i'm initializing the values in Fragment every time (slow).

      /*  save = itemView.findViewById(R.id.save_select_button);
        appSelect = itemView.findViewById(R.id.app_select_button);
        dayText = itemView.findViewById(R.id.daily_limit_text);
        weekText = itemView.findViewById(R.id.weekly_limit_text);
        weekLimit = itemView.findViewById(R.id.weekly_limit);
        dayLimit = itemView.findViewById(R.id.daily_limit);
        card = itemView.findViewById(R.id.limit_card);*/
    }
    public void setSelectList(List<BlacklistEntry> entry){
        list.setValue(entry);
    }
    public LiveData<List<BlacklistEntry>> getSelectList() {
        return list;
    }
    public List<BlacklistEntry> initializeList(List<UsageTime> usage){
        List<BlacklistEntry> list = new ArrayList<BlacklistEntry>();
        for (UsageTime u: usage){
            list.add(new BlacklistEntry(u.appName));
        }
        return list;
    }
}