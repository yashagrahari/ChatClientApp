package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Validation0 {

    @SerializedName("date")
    public String date;


    @SerializedName("data")
    public List<Validation> data;

    @SerializedName("password")
    public String password;




    @SerializedName("username")
    public String username;

    @SerializedName("mobileno")
    public String mobileno;

    @SerializedName("kk")
    public String kk;

    @SerializedName("unread")
    public String unread;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Validation0(String password, String username, String mobileno, String kk, String unread, String date, List<Validation> data) {
        this.username = username;
        this.mobileno = mobileno;
        this.password = password;
        this.kk = kk;
        this.unread = unread;
        this.date = date;
        this.data = data;
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

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Validation> getData() {
        return data;
    }

    public void setData(List<Validation> data) {
        this.data = data;
    }
}
