package com.example.myapplication;

import android.util.Log;

public class DebugHelper {

    public static void printString(String message){
        Log.v("mydebug", String.valueOf(message));
    }

    public static void printInt(int message){
        Log.v("mydebug", String.valueOf(message));
    }

    public static void printLong(long message){
        Log.v("mydebug", String.valueOf(message));
    }
}
