package com.dpt.tbase.app.net;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dpt.tbase.app.application.TBaseApplication;
import com.dpt.tbase.app.base.exception.NetNotConnException;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.base.utils.NetWorkUtil;
import com.dpt.tbase.app.net.interfaces.INetStringClientCallBack;

/**
 * 网络数据请求
 * 
 * @author dupengtao@cyou-inc.com
 * 
 *         2014-4-10
 */
public abstract class TBaseNetClent {

	private static String TAG = "TBaseNetClent";
	public static final int TYPE_JSON = 1;
	public static final int TYPE_STRING = 2;

	public static void executeRequest(Context context, String url,
			Map<String, String> params, int type,
			INetStringClientCallBack callBack) {

		if (!NetWorkUtil.checkNetWork(context)) {
			LogHelper.e(TAG, "unable to connect to the network !");
			callBack.onFinishCallBack();
			throw new NetNotConnException("net not connected");
		}
		if (TextUtils.isEmpty(url)) {
			LogHelper.e(TAG, "url is null or empty !");
			throw new NullPointerException("url is null or empty !");
		}
		if (callBack == null) {
			LogHelper.e(TAG, "callback not find !");
			throw new NullPointerException("callback not find !");
		}

		if (TYPE_JSON == type) {
			TBaseApplication.getInstance().addToRequestQueue(
					getTBaseJsonRequest(context, url, callBack));
		} else {
			TBaseApplication.getInstance().addToRequestQueue(
					getTBaseStringRequest(context, url, callBack));
		}

	}

	private static TBaseJsonObjectRequest getTBaseJsonRequest(
			final Context context, String url,
			final INetStringClientCallBack callBack) {
		callBack.onStartCallBack();
		TBaseJsonObjectRequest request = new TBaseJsonObjectRequest(Method.GET,
				url, null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						callBack.onSuccessCallBack(200, null,
								response.toString());
						callBack.onFinishCallBack();
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						processError(context, callBack, error);
					}
				});
		return request;
	}

	private static StringRequest getTBaseStringRequest(final Context context,
			String url, final INetStringClientCallBack callBack) {
		callBack.onStartCallBack();

		StringRequest stringRequest = new StringRequest(url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						callBack.onSuccessCallBack(200, null,
								response.toString());
						callBack.onFinishCallBack();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						processError(context, callBack, error);
					}

				});

		return stringRequest;
	}

	private static void processError(final Context context,
			final INetStringClientCallBack callBack, VolleyError error) {
		String message = "";
		try {
			message = VolleyErrorHelper.getMessage(error, context);
			message += "statusCode" + error.networkResponse.statusCode;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			callBack.onFailureCallBack(error, message);
			callBack.onFinishCallBack();
		}

	}
}
