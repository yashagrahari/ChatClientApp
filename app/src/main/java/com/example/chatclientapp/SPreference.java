package com.example.chatclientapp;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.chatclientapp.SharedPreference.LOGGED_IN_PREF;


public class SPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }


    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }


}