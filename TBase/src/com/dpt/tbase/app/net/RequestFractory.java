package com.dpt.tbase.app.net;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
/**
 * 
 * @author dupengtao@cyou-inc.com
 *
 * 2014-5-29
 */
@SuppressWarnings("rawtypes")
public interface RequestFractory {
    Request produceStringRequest(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener);
    Request produceJsonRequest(int method, String url, Listener<JSONObject> listener, ErrorListener errorListener);
}
