package com.dpt.tbase.app.net.interfaces;

import com.dpt.tbase.app.base.engine.IUiBaseResultCallBack;
import com.dpt.tbase.app.base.utils.LogHelper;

/**
 * simple Network Access result callback by string type
 * @author dupengtao@cyou-inc.com
 *         2014-3-13
 */
public abstract class AbStrResultCallBack implements
        INetStrClientCallBack {
    private static String TAG = "AbUiCallBack";
    private IUiBaseResultCallBack<?> mUiCallBack;
    public AbStrResultCallBack(IUiBaseResultCallBack<?> uiCb) {
        mUiCallBack=uiCb;
    }

    @Override
    public void onFailureCallBack(Throwable e, String errorResponse) {
        LogHelper.e(TAG, "onFailureCallBack...", e);
        if (mUiCallBack != null) {
            mUiCallBack.onFailureResult(e, errorResponse);
        }
    }

    @Override
    public void onStartCallBack() {
        if (mUiCallBack != null) {
            mUiCallBack.onStart();
        }
    }

    @Override
    public void onFinishCallBack() {
        if (mUiCallBack != null) {
            mUiCallBack.onFinish();
        }
    }

}
