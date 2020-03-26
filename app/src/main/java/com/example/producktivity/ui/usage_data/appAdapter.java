package com.example.producktivity.ui.usage_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.ui.scrolling_to_do.InputAdapter;

public class appAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        class appViewHolder extends RecyclerView.ViewHolder {
            private final TextView appView;
            private final TextView usageTime;

            private appViewHolder(View itemView) {
                super(itemView);
                appView = itemView.findViewById(R.id.app_name);
                usagetime = itemView.findViewById(R.id.usageTime);
            }
        }

    private final LayoutInflater mInflater;
    private List<> mWords; // Cached copy of words

    appAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new appViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((appAdapter.appViewHolder)holder, position); //bruh idk why mine doesn't work
    }

    public void onBindViewHolder(appViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }


    private String longToString(long millis) {
        int hours = (int) (millis / 3600000);
        int minutes = (int) ((millis % 3600000) / 60000);
        return hours + " hours, " + minutes + " minutes"
    }
}
