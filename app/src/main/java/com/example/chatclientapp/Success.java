package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

public class Success {

    @SerializedName("insert")
    public String insert;

    @SerializedName("id")
    public String id;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Success(String id, String insert, String date, String time) {
        this.date = date;
        this.time = time;
        this.insert = insert;
        this.id=id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInsert() {
        return insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }
}
