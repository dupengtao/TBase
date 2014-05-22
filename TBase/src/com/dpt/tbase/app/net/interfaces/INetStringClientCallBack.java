package com.dpt.tbase.app.net.interfaces;

import org.apache.http.Header;
/**
 * 网络访问回调
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-13
 */
public interface INetStringClientCallBack extends INetBaseClientCallBack{
	void onSuccessCallBack(int statusCode, Header[] headers,
			String content);
}
