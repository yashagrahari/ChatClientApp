package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

public class Validation2 {

    @SerializedName("msg")
    public String msg;

    @SerializedName("name")
    public String name;


    @SerializedName("link1__username")
    public String link1__username;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink1__username() {
        return link1__username;
    }

    public void setLink1__username(String link1__username) {
        this.link1__username = link1__username;
    }
}
