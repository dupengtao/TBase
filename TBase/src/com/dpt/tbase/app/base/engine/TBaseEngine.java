package com.dpt.tbase.app.base.engine;

import android.content.Context;

import com.dpt.tbase.app.base.exception.NetNotConnException;
import com.dpt.tbase.app.net.TBaseNetClent;
import com.dpt.tbase.app.net.interfaces.AbResultCallBack;

/**
 * @author dupengtao@cyou-inc.com
 * 
 * 
 * 
 *         2014-4-13
 */
public class TBaseEngine {

    protected void excute(Context context, final IUiBaseResultCallBack<?> uiCb,
            AbResultCallBack abResultCallBack, String url) {
        try {
            TBaseNetClent.executeRequest(context, url, null,TBaseNetClent.TYPE_JSON, abResultCallBack);
        } catch (NetNotConnException e1) {
            uiCb.noNetworkEnvironment();
        } catch (NullPointerException e1) {
            uiCb.onFailureResult(e1, e1.getMessage());
        }
    }
    protected void excute4String(Context context, final IUiBaseResultCallBack<?> uiCb,
    		AbResultCallBack abResultCallBack, String url) {
    	try {
    		TBaseNetClent.executeRequest(context, url, null,TBaseNetClent.TYPE_STRING, abResultCallBack);
    	} catch (NetNotConnException e1) {
    		uiCb.noNetworkEnvironment();
    	} catch (NullPointerException e1) {
    		uiCb.onFailureResult(e1, e1.getMessage());
    	}
    }
}
