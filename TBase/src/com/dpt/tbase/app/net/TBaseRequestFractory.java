package com.dpt.tbase.app.net;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
@SuppressWarnings("rawtypes")
public class TBaseRequestFractory implements RequestFractory {

    @Override
    public Request produceStringRequest(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(method,url,listener, errorListener);
        return stringRequest;
    }

    @Override
    public Request produceJsonRequest(int method,String url,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, null, listener,errorListener);
        return request;
    }
}
