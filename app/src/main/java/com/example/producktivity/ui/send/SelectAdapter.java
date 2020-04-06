package com.example.producktivity.ui.send;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.TempBlackListEntry;
import com.example.producktivity.ui.usage_data.AppAdapter;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;

import java.util.List;


public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        public class selectViewHolder extends RecyclerView.ViewHolder {
            private final TextView dayText;
            private final TextView weekText;
            private final EditText weekLimit;
            private final EditText dayLimit;
            private final Button appSelect;
            private final CardView card;
            private final Button save;

            public selectViewHolder(View itemView) {
                super(itemView);
                save = itemView.findViewById(R.id.save_select_button);
                appSelect = itemView.findViewById(R.id.app_select_button);
                dayText = itemView.findViewById(R.id.daily_limit_text);
                weekText = itemView.findViewById(R.id.weekly_limit_text);
                weekLimit = itemView.findViewById(R.id.weekly_limit);
                dayLimit = itemView.findViewById(R.id.daily_limit);
                card = itemView.findViewById(R.id.limit_card);
                appSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.setVisibility(View.VISIBLE);
                    }
                });
            }
        }


        private final LayoutInflater mInflater;
        private List<UsageTime> data; // Cached copy of words Daniel: should change later to include all apps
        private List<TempBlackListEntry> limits;
        SelectAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public void setLimitList(List<TempBlackListEntry> l) {
            limits = l;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.single_usage_rcyclr, parent, false);
            return new selectViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            onBindViewHolder((selectViewHolder) holder, position); //bruh idk why mine doesn't work
        }


        public void onBindViewHolder(selectViewHolder holder, int position) {
            if (data != null) {
                UsageTime current = data.get(position);
                holder.appSelect.setText(current.appName);
                holder.save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        holder.card.setVisibility(View.INVISIBLE);
                        TempBlackListEntry input = new TempBlackListEntry();
                        if (holder.appSelect.getText() != null)
                            input.setAppName(holder.appSelect.getText().toString());
                        if (holder.dayLimit.getText() != null)
                            input.setDayLimit(TempBlackListEntry.stringToLong(
                                holder.dayLimit.getText().toString()));
                        if (holder.weekLimit.getText() != null)
                            input.setWeekLimit(TempBlackListEntry.
                                stringToLong(holder.weekLimit.getText().toString()));
                        limits.add(position, input);
                        notifyItemChanged(position); //this is useful if we want to display the limits
                    }
                });;
            } else {
                // Covers the case of data not being ready yet.
                holder.appSelect.setText("No apps found");
            }

        }


        public void setLimit(List<TempBlackListEntry> a) {
            limits = a;
        }

        public void setData(List<UsageTime> datas) {
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


