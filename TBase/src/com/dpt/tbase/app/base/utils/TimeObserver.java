package com.dpt.tbase.app.base.utils;

import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 时间倒计时监听类
 * 
 * @author dupengtao@cyou-inc.com
 *         2014-3-20
 */
@SuppressLint("SimpleDateFormat")
public abstract class TimeObserver implements Observer {

    private String expireDate;
    private int times=1;
    public TimeObserver() {
        super();
    }
    public TimeObserver(String expireDate) {
        super();
        this.expireDate = expireDate;
    }
    @Override
    public void update(Observable observable, Object data) {
        if (TextUtils.isEmpty(expireDate)) {
            observable.deleteObserver(this);
            return;
        }
        long l=Long.valueOf(expireDate)-1000*(times++);
        changeTime(getExpireDate(String.valueOf(l)));
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    private String getExpireDate(String expireDate) {
        String surplusDate = null;
        long interval = Long.valueOf(expireDate);
        if (interval <= 0) {
            surplusDate = "";
        } else {
            long days = interval / (1000 * 60 * 60 * 24);

            String dDays = days + "";
            if (days < 10) {
                dDays = "0" + days;
            }
            long hours = (interval - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            String dHours = hours + "";
            if (hours < 10) {
                dHours = "0" + hours;
            }
            long minutes = (interval - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);
            String dMinutes = minutes + "";
            if (minutes < 10) {
                dMinutes = "0" + minutes;
            }
            long seconds = (interval - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
            String dSeconds = seconds + "";
            if (seconds < 10) {
                dSeconds = "0" + seconds;
            }
            if (days >= 30) {
                dDays = 6 + "";
            }
            surplusDate = dDays + "天" + dHours + "小时" + dMinutes + "分"
                    + dSeconds + "秒下架";
            boolean isTimeOver = days <= 0 && hours <= 0 && minutes <= 0
                    && seconds <= 0;
            if (isTimeOver) {
                onTimeOver();
                return "";// throw exception
            }
        }
        return surplusDate;
    }

    public abstract void changeTime(String changeTime);
    public abstract void onTimeOver();

}
