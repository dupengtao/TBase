package com.dpt.tbase.app.net.interfaces;

import org.apache.http.Header;

import com.dpt.tbase.app.base.engine.IUiBaseResultCallBack;
import com.dpt.tbase.app.base.utils.LogHelper;

/**
 * 简单封装网络访问回调
 * @author dupengtao@cyou-inc.com
 *         2014-3-13
 */
public abstract class AbResultCallBack implements
        INetStringClientCallBack {
    private static String TAG = "AbUiCallBack";
    private IUiBaseResultCallBack<?> mUiCallBack;
    public AbResultCallBack(IUiBaseResultCallBack<?> uiCb) {
        mUiCallBack=uiCb;
    }

    @Override
    public void onSuccessCallBack(int statusCode, Header[] headers,
            String response) {
        getSuccessData(statusCode, headers, response);
    }

    @Override
    public void onFailureCallBack(Throwable e, String errorResponse) {
        LogHelper.e(TAG, "http error", e);
        getError(e, errorResponse);
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

    /**
     * 获取成功responseJson，在方法中进行解析
     * 
     * @param statusCode http statusCode
     * @param headers
     * @param responseJson 错误数据json
     */
    public abstract void getSuccessData(int statusCode, Header[] headers, String responseJson);

    /**
     * 数据获取失败
     * 
     * @param e
     * @param errorResponse
     */
    public void getError(Throwable e, String errorResponse) {
        LogHelper.e(TAG, errorResponse, e);
    }

}
