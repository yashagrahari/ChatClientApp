package com.example.chatclientapp;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class SPreference {



    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("loggedstatus", loggedIn);
        editor.apply();
    }


    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean("loggedstatus", false);
    }


}
