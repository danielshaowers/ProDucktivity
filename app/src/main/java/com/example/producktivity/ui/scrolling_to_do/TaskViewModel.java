package com.example.producktivity.ui.scrolling_to_do;

import android.renderscript.ScriptGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
public class TaskViewModel extends ViewModel {

        //Live data updates user profile fragment when data is updated
        private MutableLiveData<String> mText;
        private MutableLiveData<InputAdapter> inputAdapter;
        private MutableLiveData<ArrayList<Info>> tasks;
        private SavedStateHandle mState; //if I want to use this it'd be cool


      /*  public TaskViewModel(SavedStateHandle savedStateHandle) {
            mState = savedStateHandle;
            mText = new MutableLiveData<>();
            mText.setValue("Create and view tasks here");
            //
            SavedStateViewModel vm = new ViewModelProvider((ViewModelStoreOwner) this).get(SavedStateViewModel.class);
        }*/

      public TaskViewModel(){
          mText = new MutableLiveData<>();
          mText.setValue("Create and view tasks here");
         /* inputAdapter = new MutableLiveData<>(); not sure if this belongs in taskview or fragment
          inputAdapter.setValue(new InputAdapter(tasks.getValue())); */
      }
        //this doesn't work, so I will do it in input adapter, not here
        public LiveData<InputAdapter> getAdapter() {
            return inputAdapter;
        }

        public LiveData<ArrayList<Info>> getTasks(){
          return tasks;
        }



        public LiveData<String> getText() {
            return mText;
        }
}

