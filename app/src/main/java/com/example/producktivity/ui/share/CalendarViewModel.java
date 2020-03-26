package com.example.producktivity.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*?
 Standard view model class which is overwritten to show the activity
 */
public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}