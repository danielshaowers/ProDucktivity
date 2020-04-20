package com.example.producktivity.ui.scrolling_to_do;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.AppUtils;
import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

//import static com.example.flashcards.TaskFragment.GET_FROM_GALLERY; //not sure if it should be input activity

//this class creates views for data, and replaces the content of views when they are no longer available
//dang it i can't figure out how to make the inputviewHolder work as a static class instead of nonstatic class
public class InputAdapter extends RecyclerView.Adapter<InputAdapter.TaskViewHolder> {

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd hh:mm a", Locale.US);

    private List<Task> tasks; //cached copy of words
    private OnTaskItemClick onTaskItemClick;


    //obtains the layoutinflater from the given context. layoutinflater converts the xml file into its corresponding view
    InputAdapter(Fragment frag, List<Task> tasks) {
        Objects.requireNonNull(tasks);
        this.tasks = tasks;

        onTaskItemClick = (OnTaskItemClick) frag;
    }

    @Override
    @NonNull
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task_rcyclr, parent, false);
        return new TaskViewHolder(itemView);
    }

    //TaskViewHolder is the current view of our cardview
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        System.out.println("current task at position " + position + " is " + current.getTitle());
        holder.title.setText(current.getTitle());

        if (current.getDueDate() != null)
            holder.date.setText(dateFormat.format(current.getDueDate())); //formats in mm/dd form //not sure if this one is correct
        //MainActivity.makeCalendar(holder.date, this.mContext);
        holder.desc.setText(current.getDesc());
        if (current.getReminderTime() != null)
            holder.reminder.setText(dateFormat.format(current.getReminderTime())); //formats in mm/dd form


        switch (current.getPriority()) {
            case HIGH:
                holder.priority.setText("High Priority");
                break;
            case MED:
                holder.priority.setText("Medium Priority");
                break;
            case LOW:
                holder.priority.setText("Low Priority");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Task getItemAt(int pos) {
        return tasks.get(pos);
    }

    public void addTasks(List<Task> newTasks) {
        tasks = newTasks;
        notifyDataSetChanged();
    }

    public interface OnTaskItemClick {
        void onTaskClick(int pos);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView date;
        private TextView desc;
        private TextView reminder;
        private TextView priority;

        private TaskViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.todo_title_view);
            date = itemView.findViewById(R.id.todo_date_view);
            desc = itemView.findViewById(R.id.todo_description_view);
            reminder = itemView.findViewById(R.id.todo_reminder_view);
            priority = itemView.findViewById(R.id.priority_view);

        }

        @Override
        public void onClick(View v) {
            onTaskItemClick.onTaskClick(getAdapterPosition());
        }

    }
}

