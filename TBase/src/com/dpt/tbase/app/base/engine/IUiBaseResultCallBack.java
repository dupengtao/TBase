package com.dpt.tbase.app.base.engine;

import org.apache.http.Header;

/**
 * 对返回结果进行封装
 * 
 * @author dupengtao@cyou-inc.com
 *         2014-3-18
 */
public interface IUiBaseResultCallBack<T> {

    void onSuccessResult(T datas, int statusCode, Header[] headers,
            String content);

    void onFailureResult(Throwable e, String content);

    void onStart();

    void onFinish();

    void noNetworkEnvironment();
}
