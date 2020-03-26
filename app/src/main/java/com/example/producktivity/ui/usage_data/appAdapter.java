package com.example.producktivity.ui.usage_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;

import java.util.List;

public class appAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private List<UsageDataHandler.UsageTime> data; // Cached copy of words

    appAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_usage_rcyclr, parent, false);
        return new appViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((appAdapter.appViewHolder) holder, position); //bruh idk why mine doesn't work
    }


    public void onBindViewHolder(appViewHolder holder, int position) {
        if (data != null) {
            UsageDataHandler.UsageTime current = data.get(position);
            holder.appView.setText(current.appName);
            holder.usageView.setText(longToString(current.millisUsed)); //converts milliseconds to
        } else {
            // Covers the case of data not being ready yet.
            holder.usageView.setText("No apps found");
        }

    }



    void setData(List<UsageDataHandler.UsageTime> datas) {
        data = datas;
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
        int hours = (int) (millis / 3600000);
        int minutes = (int) ((millis % 3600000) / 60000);
        return hours + " hours, " + minutes + " minutes";
    }


}

