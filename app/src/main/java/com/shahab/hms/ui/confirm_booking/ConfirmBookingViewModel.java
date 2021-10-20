package com.shahab.hms.ui.confirm_booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfirmBookingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfirmBookingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}