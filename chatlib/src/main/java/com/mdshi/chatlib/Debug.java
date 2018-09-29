package com.mdshi.chatlib;

import android.util.Log;

/**
 * Created by MaDeng on 2018/9/3.
 */
public  class Debug {
    public boolean isDebug = false;

    private static final String TAG = "IMChat";
    private Debug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    private Debug() {

    }

    public static Debug debug;

    public static void init(boolean isDebug) {
        debug = new Debug(isDebug);
    }
    public static void  e(String s) {
        if (debug != null&&debug.isDebug) {
            Log.e(TAG,s);
        }
    }
    public static void  e(String s,Throwable t) {
        if (debug != null&&debug.isDebug) {
            Log.e(TAG,s,t);
        }
    }
    public static void  d(String s) {
        if (debug != null&&debug.isDebug) {
            Log.d(TAG,s);
        }
    }

}
