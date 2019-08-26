package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserAdminmessagesandactivity3 {

    @SerializedName("date")
    public String date;


    @SerializedName("data")
    public List<MessageData> data;

    @SerializedName("password")
    public String password;

    @SerializedName("cc")
    public String cc;

    @SerializedName("kk")
    public String kk;




    @SerializedName("username")
    public String username;

    @SerializedName("mobileno")
    public String mobileno;



    @SerializedName("unread")
    public String unread;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public UserAdminmessagesandactivity3(String kk, String password, String username, String mobileno, String cc, String unread, String date, List<MessageData> data) {
        this.username = username;
        this.mobileno = mobileno;
        this.password = password;
        this.kk=kk;
        this.unread = unread;
        this.date = date;
        this.data = data;
        this.cc=cc;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MessageData> getData() {
        return data;
    }

    public void setData(List<MessageData> data) {
        this.data = data;
    }
}
