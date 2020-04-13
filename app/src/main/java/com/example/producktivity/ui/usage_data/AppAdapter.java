package com.example.producktivity.ui.usage_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.BlacklistEntry;
import com.example.producktivity.ui.send.BlockSelectViewModel;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class appViewHolder extends RecyclerView.ViewHolder {
        private final TextView appView;
        private final TextView usageView;

        private appViewHolder(View itemView) {
            super(itemView);
            appView = itemView.findViewById(R.id.app_name);
            usageView = itemView.findViewById(R.id.usage_data);
        }
    }


    private final LayoutInflater mInflater;
    private List<BlacklistEntry> data; // Cached copy of words

    AppAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_usage_rcyclr, parent, false);
        return new appViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((AppAdapter.appViewHolder) holder, position); //bruh idk why mine doesn't work
    }


    public void onBindViewHolder(appViewHolder holder, int position) {
        if (data != null) {
            BlacklistEntry current = data.get(position);
            holder.appView.setText(current.getAppName());
            holder.usageView.setText(longToString(current.getTimeOfFlag(current.getSpan_flag()))); //converts milliseconds to
        } else {
            // Covers the case of data not being ready yet.
            holder.usageView.setText("No apps found");
        }

    }


public List<BlacklistEntry> getData(){return data;}

    public void setData(List<BlacklistEntry> datas) {
        data = BlockSelectViewModel.sortData(datas);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    private String longToString(long millis) {
        String output = "";
        long minutes = millis / 60000;
        if (minutes > 1440) {
            output += (minutes / 1440) + "d ";
            minutes %= 1440;
        }
        if (minutes > 60) {
            output += (minutes / 60) + "h ";
            minutes %= 60;
        }
        return output + minutes + "m";
    }


}

