package com.shahab.hms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class Profile {
    ////name email phone address bio
    String name, email, contactno, address, bio;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Profile(String name, String email, String contactno, String address, String bio) {
        this.name = name;
        this.email = email;
        this.contactno = contactno;
        this.address = address;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
