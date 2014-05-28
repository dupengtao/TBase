package com.dpt.tbase.app.net;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dpt.tbase.app.application.TBaseApplication;
import com.dpt.tbase.app.base.exception.NetNotConnException;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.base.utils.NetWorkUtil;
import com.dpt.tbase.app.net.interfaces.INetBaseClientCallBack;
import com.dpt.tbase.app.net.interfaces.INetJsonClientCallBack;
import com.dpt.tbase.app.net.interfaces.INetStrClientCallBack;

/**
 * 网络数据请求
 * 
 * @author dupengtao@cyou-inc.com
 *         2014-4-10
 */
public abstract class TBaseNetClent {

    private static String TAG = "TBaseNetClent";
    public static final int TYPE_JSON = 1;
    public static final int TYPE_STRING = 2;

    public static void executeRequest(Context context, String url, Map<String, String> params, int type,
            INetBaseClientCallBack callBack) {
        executeRequest(context, url, params, type, true, null, callBack);
    }

    public static void executeRequest(Context context, String url, Map<String, String> params, int type,
            boolean isShouldCache,String resTag,INetBaseClientCallBack callBack) {
        check(context, url, callBack);
        if (TYPE_JSON == type) {
            INetJsonClientCallBack jsonClientCallBack = (INetJsonClientCallBack) callBack;
            TBaseJsonObjectRequest request = getTBaseJsonRequest(context, url, jsonClientCallBack);
            setRequestParams(isShouldCache, resTag, request);
            
        } else {
            INetStrClientCallBack strClientCallBack = (INetStrClientCallBack) callBack;
            StringRequest request = getTBaseStringRequest(context, url, strClientCallBack);
            setRequestParams(isShouldCache, resTag, request);
        }

    }

    public static void setRequestParams(boolean isShouldCache, String resTag, Request<?> request) {
        if(!isShouldCache){
            request.setShouldCache(false);
        }
        if(TextUtils.isEmpty(resTag)){
            TBaseApplication.getInstance().addToRequestQueue(request);
        }else{
            TBaseApplication.getInstance().addToRequestQueue(request,resTag);
        }
    }

    public static void check(Context context, String url, INetBaseClientCallBack callBack) {
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
    }

    private static TBaseJsonObjectRequest getTBaseJsonRequest(
            final Context context, String url,
            final INetJsonClientCallBack callBack) {
        callBack.onStartCallBack();
        TBaseJsonObjectRequest request = new TBaseJsonObjectRequest(Method.GET,
                url, null, new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccessCallBack(response);
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
            String url, final INetStrClientCallBack callBack) {
        callBack.onStartCallBack();

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBack.onSuccessCallBack(200, response.toString(), null);
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
            final INetBaseClientCallBack callBack, VolleyError error) {
        String message = "";
        try {
            message = VolleyErrorHelper.getMessage(error, context);
            message += "statusCode" + error.networkResponse.statusCode;
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            callBack.onFailureCallBack(error, message);
            callBack.onFinishCallBack();
        }
    }

    public static void executeImageRequest(String requestUrl, ImageView imageView, int roadingResId,
            int errorResId) {
        TBaseApplication.getInstance().getImageLoader().get(requestUrl, ImageLoader.getImageListener(
                imageView, roadingResId, errorResId));
    }
}
