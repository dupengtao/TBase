package com.dpt.tbase.app.base.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

public class LogHelper {
    private static final boolean DEBUG_LOG = true;

    public static void v(String subTag, String msg) {
        if (DEBUG_LOG) {
            Log.v(subTag, getLogMsg(subTag, msg));
        }
    }

    public static void d(String subTag, String msg) {
        if (DEBUG_LOG) {
            Log.d(subTag, getLogMsg(subTag, msg));
        }
    }

    public static void i(String subTag, String msg) {
        if (DEBUG_LOG) {
            Log.i(subTag, getLogMsg(subTag, msg));
        }
    }

    public static void w(String subTag, String msg) {
        if (DEBUG_LOG) {
            Log.w(subTag, getLogMsg(subTag, msg));
        }
    }

    public static void w(String subTag, String msg, Throwable e) {
        if (DEBUG_LOG) {
            Log.w(subTag, getLogMsg(subTag, msg + " Exception: " + getExceptionMsg(e)));
        }
    }

    public static void e(String subTag, String msg) {
        if (DEBUG_LOG) {
            Log.e(subTag, getLogMsg(subTag, msg));
        }
    }

    public static void e(String subTag, String msg, Throwable e) {
        if (DEBUG_LOG) {
            Log.e(subTag, getLogMsg(subTag, msg + " Exception: " + getExceptionMsg(e)));
        }
    }

    private static String getLogMsg(String subTag, String msg) {
        return "[" + subTag + "] " + msg;
    }

    private static String getExceptionMsg(Throwable e) {
        StringWriter sw = new StringWriter(1024);
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }
}
