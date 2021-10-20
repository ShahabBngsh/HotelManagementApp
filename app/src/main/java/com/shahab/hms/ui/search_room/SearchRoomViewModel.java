package com.shahab.hms.ui.search_room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchRoomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchRoomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}