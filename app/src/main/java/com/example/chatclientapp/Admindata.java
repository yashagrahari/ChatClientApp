package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

public class Admindata {

    @SerializedName("username")
    public String username;

    @SerializedName("mobileno")
    public String mobileno;

    @SerializedName("unread")
    public String unread;

    public Admindata(String username, String mobileno, String unread) {
        this.username = username;
        this.mobileno = mobileno;
        this.unread = unread;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }
}
