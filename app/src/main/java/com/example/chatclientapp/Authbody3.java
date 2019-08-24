package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

public class Authbody3 {


    @SerializedName("message")
    public String user_message;

    @SerializedName("name")
    public String name;


    @SerializedName("username")
    public String username;




    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
