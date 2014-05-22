package com.dpt.tbase.app.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkUtil {

    private static final String TAG = "NetWorkUtil";
    private static final Class<NetWorkUtil> clazz = NetWorkUtil.class;

    /** Same to {@link ConnectivityManager#TYPE_WIMAX} (API 8) */
    private static final int CM_TYPE_WIMAX = 6;
    /** Same to {@link ConnectivityManager#TYPE_ETHERNET} (API 13) */
    private static final int CM_TYPE_ETHERNET = 9;
    /** Same to {@link ConnectivityManager#TYPE_MOBILE_MMS} (API 8) */
    private static final int CM_TYPE_MOBILE_MMS = 2;
    /** Same to {@link ConnectivityManager#TYPE_BLUETOOTH} (API 8) */
    private static final int CM_TYPE_BLUETOOTH = 7;

    /** Same to {@link TelephonyManager#NETWORK_TYPE_EVDO_B} (API 9) 5 Mbps */
    private static final int TM_NETWORK_TYPE_EVDO_B = 12;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_LTE} (API 11) 10+ Mbps */
    private static final int TM_NETWORK_TYPE_LTE = 13;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_EHRPD} (API 11) 1~2 Mbps */
    private static final int TM_NETWORK_TYPE_EHRPD = 14;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_HSPAP} (API 13) 10~20 Mbps */
    private static final int TM_NETWORK_TYPE_HSPAP = 15;

    public static final int NET_TYPE_NONE = -1;
    // Don't touch! The following network types are defined by Server.
    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_2G = 2;
    public static final int NET_TYPE_3G = 3;
    public static final int NET_TYPE_MOBILE = 4;

    private static ConnectivityManager sCM;

    private static ConnectivityManager getConnectivityManager(Context context) {
        if (sCM == null) {
            sCM = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
        }
        return sCM;
    }

    /**
     * @param
     * @return One of values {@link #NET_TYPE_WIFI}, {@link #NET_TYPE_2G}, {@link #NET_TYPE_3G} or
     *         {@link #NET_TYPE_NONE}
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connMgr = getConnectivityManager(context);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null) {
            int type = netInfo.getType();
            int subType = netInfo.getSubtype();
            if (type == ConnectivityManager.TYPE_WIFI
                    || type == CM_TYPE_WIMAX
                    || type == CM_TYPE_ETHERNET) {
                return NET_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE
                    /*
                     * this patch for fix in some devices type when apn connected, report type is
                     * TYPE_BLUETOOTH and has subtype.  tested on CoolPad 7260+
                     */
                    || (type == CM_TYPE_BLUETOOTH && subType > 0)) {
                if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                        || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_HSUPA
                        || subType == TelephonyManager.NETWORK_TYPE_HSPA
                        || subType == TM_NETWORK_TYPE_EVDO_B
                        || subType == TM_NETWORK_TYPE_LTE
                        || subType == TM_NETWORK_TYPE_EHRPD
                        || subType == TM_NETWORK_TYPE_HSPAP) {
                    return NET_TYPE_3G;
                }
                return NET_TYPE_2G; // Take other data types as 2G
            } else if (type == CM_TYPE_MOBILE_MMS || type == CM_TYPE_BLUETOOTH) {
                // when mms and bluetooth, don't recognize as mobile
                return NET_TYPE_NONE;
            }
            return NET_TYPE_2G; // Take unknown networks as 2G
        }
        return NET_TYPE_NONE;
    }

    /**
     * @param context
     * @return One of the values {@link #NET_TYPE_NONE} or {@link #NET_TYPE_WIFI} or {@link #NET_TYPE_MOBILE}
     */
    public static int getSimpleNetworkType(Context context) {
        ConnectivityManager connMgr = getConnectivityManager(context);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null) {
            int type = netInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI || type == CM_TYPE_WIMAX
                    || type == CM_TYPE_ETHERNET) {
                return NET_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return NET_TYPE_MOBILE;
            } else if (type == CM_TYPE_MOBILE_MMS || type == CM_TYPE_BLUETOOTH) {
                return NET_TYPE_NONE;
            } else {
                // Take unknown networks as mobile network
                return NET_TYPE_MOBILE;
            }
        }
        return NET_TYPE_NONE;
    }

    /**
     * 检查网络
     * 
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        // ConnectivityManager//系统服务
        // ①判断WIFI联网情况
        boolean isWifi = isWifi(context);
        // ②判断MOBILE联网情况
        boolean isMobile = isMobile(context);

        if (!isWifi && !isMobile) {
            // 如果都不能联网，提示用户
            return false;
        }
        return true;
    }

    /**
     * 判断wifi是否处于连接状态
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // NetworkInfo:支持WIFI和MOBILE
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }

    /**
     * 判断Mobile是否处于连接状态
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        if (cm == null) {
            return false;
        }
        // NetworkInfo:支持WIFI和MOBILE
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }
}
