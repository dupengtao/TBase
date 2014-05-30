package com.dpt.tbase.app.net;

import java.io.File;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dpt.tbase.app.application.TBaseApplication;
import com.dpt.tbase.app.base.exception.NetNotConnException;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.base.utils.NetWorkUtil;
import com.dpt.tbase.app.net.interfaces.INetBaseClientCallBack;
import com.dpt.tbase.app.net.interfaces.INetJsonClientCallBack;
import com.dpt.tbase.app.net.interfaces.INetStrClientCallBack;

/**
 * 运用了抽象工厂设计模式
 * 
 * @author dupengtao@cyou-inc.com
 *
 * 2014-5-29
 */
@SuppressWarnings("rawtypes")
public class TBaseNetClent2 {

    public final static int TYPE_JSON = 1;
    public final static int TYPE_STRING = 2;
    private static TBaseNetClent2 netClent2;
    private RequestFractory mRequestFractory;
    private String TAG = TBaseNetClent2.class.getSimpleName();
    
    private TBaseNetClent2(RequestFractory requestFractory) {
        mRequestFractory = requestFractory;
    }
    /**
     * if requestFractory is null use {@link TBaseRequestFractory}
     * 
     */
    public static TBaseNetClent2 getInstance(RequestFractory requestFractory){
        if(netClent2==null){
            if(requestFractory==null){
                requestFractory= new TBaseRequestFractory();
            }
            netClent2 = new TBaseNetClent2(requestFractory);
        }
        return netClent2;
    }
    
    public void setRequestFractory(RequestFractory requestFractory) {
        if(requestFractory==null){
            requestFractory= new TBaseRequestFractory();
        }
       mRequestFractory = requestFractory;
    }
    /**
     * custom request 
     */
    public void executeRequest(Request request) {
        executeRequest(true, null, request);
    }
    
    public void executeRequest(boolean isShouldCache, String resTag ,Request<?> request) {
        addQueue(isShouldCache, resTag, request);
    }
    
    public void executeRequest(Context context,int method, String url, int type, INetBaseClientCallBack callBack) {
        executeRequest(context,method,url,type, true, null, callBack);
    }
    /**
     * @param method @see {@link Request.Method}
     * @param type @see {@link TBaseNetClent2#TYPE_JSON} {@link TBaseNetClent2#TYPE_STRING}
     * @param isShouldCache true add cache 
     * @param resTag add request by tag
     * @param callBack {@link INetBaseClientCallBack}
     */
    public void executeRequest(Context context,int method,String url, int type, boolean isShouldCache, String resTag, INetBaseClientCallBack callBack) {
        check(context, url, callBack);
        Request request ;
        if (TYPE_STRING == type) {
            INetStrClientCallBack strClientCallBack = (INetStrClientCallBack) callBack;
            request = getStringRequest(context,method, url, strClientCallBack);
        } else{
            INetJsonClientCallBack jsonClientCallBack = (INetJsonClientCallBack) callBack;
            request = getJsonRequest(context,method, url, jsonClientCallBack);
        }
        addQueue(isShouldCache, resTag, request);
    }
    
    public void executeImageRequest(String requestUrl, ImageView imageView, int roadingResId,int errorResId) {
        TBaseApplication.getInstance().getImageLoader().get(requestUrl, ImageLoader.getImageListener(imageView, roadingResId, errorResId));
    }
    
    public void cacheRemove(String url){
        TBaseApplication.getInstance().getRequestQueue().getCache().remove(url);
    }
    public void cacheClear(){
        TBaseApplication.getInstance().getRequestQueue().getCache().clear();
    }
    public void cancelSingleRequest(String reqTag){
        TBaseApplication.getInstance().getRequestQueue().cancelAll(reqTag);
    }
    public void cancelAllRequests(){
        TBaseApplication.getInstance().getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
    /**
     * count cache size
     */
    public long getCacheSize(Context context){
        File cacheDir = new File(context.getCacheDir(), "volley");
        return cacheDir.length();
    }
    
    private void addQueue(boolean isShouldCache, String resTag, Request<?> request) {
        if (!isShouldCache) {
            request.setShouldCache(false);
        }
        if (TextUtils.isEmpty(resTag)) {
            TBaseApplication.getInstance().addToRequestQueue(request);
        } else {
            TBaseApplication.getInstance().addToRequestQueue(request, resTag);
        }
    }
    private void check(Context context, String url, INetBaseClientCallBack callBack) {
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
    private Request getJsonRequest( final Context context,int method, String url,
            final INetJsonClientCallBack callBack) {
        callBack.onStartCallBack();
        Listener<JSONObject> listener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccessCallBack(response);
                callBack.onFinishCallBack();
            }
        };
        ErrorListener errorListener = getErrorListener(context, callBack);
        return mRequestFractory.produceJsonRequest(method, url, listener, errorListener);
    }
    
    private Request getStringRequest(final Context context,int method,
            String url, final INetStrClientCallBack callBack) {
        callBack.onStartCallBack();
        Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onSuccessCallBack(200, response.toString(), null);
                callBack.onFinishCallBack();
            }
        };
        ErrorListener errorListener = getErrorListener(context, callBack);
        return mRequestFractory.produceStringRequest(method, url, listener, errorListener);
    }

    private ErrorListener getErrorListener(final Context context, final INetBaseClientCallBack callBack) {
        ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                processError(context, callBack, error);
            }

        };
        return errorListener;
    }

    private void processError(final Context context,
            final INetBaseClientCallBack callBack, VolleyError error) {
        String message = "";
        try {
            message = VolleyErrorHelper.getMessage(error, context);
            message += "statusCode" + error.networkResponse.statusCode;
            LogHelper.e(TAG, message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            callBack.onFailureCallBack(error, message);
            callBack.onFinishCallBack();
        }
    }
}
