package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

public class AuthBody {

    @SerializedName("username")
    public String username;


    @SerializedName("password")
    public String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
