package com.dpt.tbase.app.net.interfaces;

/**
 * 网络访问回调
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-13
 */
public interface INetStrClientCallBack extends INetBaseClientCallBack{
    /**
     * on success callback
     * @param otherMsg some message default is null
     */
	void onSuccessCallBack(int statusCode,String content,String[] otherMsg);
}
