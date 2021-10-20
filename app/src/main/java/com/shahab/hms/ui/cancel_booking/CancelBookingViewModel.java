package com.shahab.hms.ui.cancel_booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CancelBookingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CancelBookingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is weird fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}