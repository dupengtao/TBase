package com.dpt.tbase.app.net;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
/**
 * 
 * @author dupengtao@cyou-inc.com
 * @deprecated 此类已放到Treader中
 * 2014-6-3
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class TReaderRequestFractory implements RequestFractory {

    @Override
    public Request produceStringRequest(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(method,url,listener, errorListener);
        return stringRequest;
    }

    @Override
    public Request produceJsonRequest(int method,String url,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener) {
        TBaseJsonObjectRequest request = new TBaseJsonObjectRequest(method,url, null,listener,errorListener);
        return request;
    }
}
