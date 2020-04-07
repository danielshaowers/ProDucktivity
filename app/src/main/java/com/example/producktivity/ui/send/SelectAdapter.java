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
import com.example.producktivity.dbs.BlacklistEntry;
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
                        appSelect.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }


        private final LayoutInflater mInflater;
        private List<BlacklistEntry> limits;
        SelectAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public void setLimitList(List<BlacklistEntry> l) {
            limits = l;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.single_select_rcyclr, parent, false);
            return new selectViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            onBindViewHolder((selectViewHolder) holder, position);
        }


        public void onBindViewHolder(selectViewHolder holder, int position) {
            if (limits != null) {
                BlacklistEntry current = limits.get(position);
                holder.appSelect.setText(current.getAppName());
                holder.weekLimit.setText(longToString(current.getWeekLimit()));
                holder.dayLimit.setText(longToString(current.getDayLimit()));
                holder.save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){ //when done is clicked, hide and save the values
                        holder.card.setVisibility(View.INVISIBLE);
                        holder.appSelect.setVisibility(View.VISIBLE);
                        //if (holder.appSelect.getText().toString() != null) not necessary
                           // input.setAppName(holder.appSelect.getText().toString());
                        if (holder.dayLimit.getText().toString().length() > 0) {
                            long length = BlacklistEntry.stringToLong(holder.dayLimit.getText().toString());
                            current.setDayLimit(length);
                            holder.dayLimit.setText(longToString(length));
                        }
                        if (holder.weekLimit.getText().toString().length() > 0) {
                            long length = (BlacklistEntry.stringToLong(holder.weekLimit.getText().toString()));
                            current.setWeekLimit(length);
                            holder.weekLimit.setText(longToString(length));
                        }
                        limits.set(position, current);
                        notifyItemChanged(position); //this is useful if we want to display the limits
                    }
                });;
            } else {
                // Covers the case of data not being ready yet.
                holder.appSelect.setText("No apps found");
            }
        }


        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (limits != null)
                return limits.size();
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


