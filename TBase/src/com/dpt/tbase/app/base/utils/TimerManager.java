package com.dpt.tbase.app.base.utils;

import java.util.Observable;


import android.os.CountDownTimer;

public class TimerManager extends Observable {

    private static CountDownTimer mTimer;

    public static CountDownTimer initTimer() {
        if (mTimer == null) {
            mTimer = new CountDownTimer(Integer.MAX_VALUE, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    LogHelper.w("倒计时", String.valueOf(millisUntilFinished));
                    timerManager.setChanged();
                    timerManager.notifyObservers();
                }

                @Override
                public void onFinish() {
                }
            };
        }

        return mTimer;
    }

    private static TimerManager timerManager;

    private TimerManager() {
        super();
    }

    public static TimerManager initTimerManager() {
        if (timerManager == null) {
            timerManager = new TimerManager();
            initTimer();
            mTimer.start();
        }
        return timerManager;
    }
    
    public static void stop() {
        mTimer=null;
        timerManager=null;
    }
}
