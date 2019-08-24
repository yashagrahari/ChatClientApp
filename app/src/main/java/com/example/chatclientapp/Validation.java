package com.example.chatclientapp;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;


public class Validation {

    @SerializedName("message")
    public String msg;

    @SerializedName("name")
    public String name;


    @SerializedName("link__username")
    public String link1__username;



    @SerializedName("dtoi")
    public String dtoi;


    @SerializedName("status")
    public String status;

    @SerializedName("id")
    public String id;

    public String getDtoi() {
        return dtoi;
    }

    public void setDtoi(String dtoi) {
        this.dtoi = dtoi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Validation(String msg, String name, String link1__username, String username, String mobileno, String kk, String unread, String dtoi, String id, String status) {

        this.msg = msg;
        this.name = name;
        this.link1__username = link1__username;

        this.dtoi=dtoi;
        this.status=status;
        this.id=id;
    }

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