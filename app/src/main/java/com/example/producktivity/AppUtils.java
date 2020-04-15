package com.example.producktivity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.producktivity.dbs.todo.Task;

import java.util.List;

public class AppUtils {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public class TaskDiffUtil extends DiffUtil.Callback {

        List<Task> oldTaskList;
        List<Task> newTaskList;
        public TaskDiffUtil(List<Task> oldNoteList, List<Task> newNoteList) {
            this.oldTaskList = oldNoteList;
            this.newTaskList = newNoteList;
        }

        @Override
        public int getOldListSize() {
            return oldTaskList.size();
        }

        @Override
        public int getNewListSize() {
            return newTaskList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldTaskList.get(oldItemPosition).getId() == newTaskList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldTaskList.get(oldItemPosition).equals(newTaskList.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            //you can return particular field for changed item.
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
