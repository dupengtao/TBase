package com.dpt.tbase.app.net.interfaces;

/**
 * 网络访问回调
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-13
 */
public interface INetBaseClientCallBack{
	void onFailureCallBack(Throwable e, String content);
	void onStartCallBack();
	void onFinishCallBack();
}
