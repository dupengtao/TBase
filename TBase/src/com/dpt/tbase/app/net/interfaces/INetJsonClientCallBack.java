package com.dpt.tbase.app.net.interfaces;

import org.json.JSONObject;
/**
 * 网络访问回调
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-13
 */
public interface INetJsonClientCallBack extends INetBaseClientCallBack{
	void onSuccessCallBack(JSONObject jsonObject);
}
