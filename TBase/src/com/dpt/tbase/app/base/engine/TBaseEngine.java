package com.dpt.tbase.app.base.engine;

import android.content.Context;

import com.android.volley.Request;
import com.dpt.tbase.app.base.exception.NetNotConnException;
import com.dpt.tbase.app.net.RequestFractory;
import com.dpt.tbase.app.net.TBaseNetClent2;
import com.dpt.tbase.app.net.interfaces.AbJsonResultCallBack;
import com.dpt.tbase.app.net.interfaces.AbStrResultCallBack;

/**
 * @author dupengtao@cyou-inc.com 2014-4-13
 */
public class TBaseEngine {

	/**
	 * json network request
	 * 
	 * @see {@link TBaseNetClent2#executeRequest(Context, int, String, int, boolean, String, com.dpt.tbase.app.net.interfaces.INetBaseClientCallBack)}
	 */
	protected void excute(Context context, RequestFractory requestFractory,
			int method, String url, boolean isShouldCache, String resTag,
			final IUiBaseResultCallBack<?> uiCb, AbJsonResultCallBack callBack) {
		try {
			TBaseNetClent2.getInstance(requestFractory).executeRequest(context,
					method, url, TBaseNetClent2.TYPE_JSON, isShouldCache,
					resTag, callBack);
		} catch (NetNotConnException e1) {
			uiCb.noNetworkEnvironment();
		} catch (NullPointerException e1) {
			uiCb.onFailureResult(e1, e1.getMessage());
		}
	}

	/**
	 * json network request
	 * 
	 * @see #excute(Context, RequestFractory, int, String, boolean, String,
	 *      IUiBaseResultCallBack, AbJsonResultCallBack)
	 */
	protected void excute(Context context, String url, boolean isShouldCache,
			final IUiBaseResultCallBack<?> uiCb, AbJsonResultCallBack callBack) {
		try {
		    RequestFractory fractory = TBaseNetClent2.getRequestFractory();
			TBaseNetClent2.getInstance(fractory)
					.executeRequest(context, Request.Method.GET, url,
							TBaseNetClent2.TYPE_JSON, callBack);
		} catch (NetNotConnException e1) {
			uiCb.noNetworkEnvironment();
		} catch (NullPointerException e1) {
			uiCb.onFailureResult(e1, e1.getMessage());
		}
	}

	/**
	 * str network request
	 * 
	 * @see {@link TBaseNetClent2#executeRequest(Context, int, String, int, boolean, String, com.dpt.tbase.app.net.interfaces.INetBaseClientCallBack)}
	 */
	protected void excuteStr(Context context, RequestFractory requestFractory,
			int method, String url, boolean isShouldCache, String resTag,
			final IUiBaseResultCallBack<?> uiCb,
			AbStrResultCallBack abStrResultCallBack) {
		try {
			TBaseNetClent2.getInstance(requestFractory).executeRequest(context,
					method, url, TBaseNetClent2.TYPE_STRING, isShouldCache,
					resTag, abStrResultCallBack);
		} catch (NetNotConnException e1) {
			uiCb.noNetworkEnvironment();
		} catch (NullPointerException e1) {
			uiCb.onFailureResult(e1, e1.getMessage());
		}
	}

	/**
	 * str network request
	 * 
	 * @see #excuteStr(Context, String, IUiBaseResultCallBack,
	 *      AbStrResultCallBack)
	 */
	protected void excuteStr(Context context, String url,
			final IUiBaseResultCallBack<?> uiCb,
			AbStrResultCallBack abStrResultCallBack) {
		try {
		    RequestFractory fractory = TBaseNetClent2.getRequestFractory();
			TBaseNetClent2.getInstance(fractory).executeRequest(context,
					Request.Method.GET, url, TBaseNetClent2.TYPE_STRING,
					abStrResultCallBack);
		} catch (NetNotConnException e1) {
			uiCb.noNetworkEnvironment();
		} catch (NullPointerException e1) {
			uiCb.onFailureResult(e1, e1.getMessage());
		}
	}
}
