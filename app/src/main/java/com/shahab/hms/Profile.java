package com.shahab.hms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Objects;

public class Profile {
    ////name email phone address bio
    String id, name, email, contactno, address, bio;
    Long dues;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Profile(String id, String name, String email, String contactno, String address, String bio, Long dues) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactno = contactno;
        this.address = address;
        this.bio = bio;
        this.dues = dues;
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

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    //    public void setId(String id) {
//        this.id = id;
//    }

    public Long getDues() {
        return dues;
    }

    public void setDues(Long dues) {
        this.dues = dues;
    }
}
