package com.example.assignment2java.aspect;

import android.util.Log;

public class DebugLog {
    private DebugLog() {}

    public static void log(String tag, String message) {
        Log.d(tag, message);
    }
}

