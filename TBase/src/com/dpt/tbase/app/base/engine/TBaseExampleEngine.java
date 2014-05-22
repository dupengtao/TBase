package com.dpt.tbase.app.base.engine;

import org.apache.http.Header;

import android.content.Context;

import com.dpt.tbase.app.net.interfaces.AbResultCallBack;

/**
 * @author dupengtao@cyou-inc.com 2014-4-13
 */
public class TBaseExampleEngine extends TBaseEngine {

	public void loadingDatas(Context context,
			final IUiBaseResultCallBack<String> uiCb) {
		AbResultCallBack abResultCallBack = new AbResultCallBack(uiCb) {
			@Override
			public void getSuccessData(int statusCode, Header[] headers,
					String responseJson) {
				// LogHelper.e("TBaseExampleEngine", responseJson);
				uiCb.onSuccessResult(responseJson, statusCode, headers,
						responseJson);
			}
		};
		String url = "http://wcf.open.cnblogs.com/news/item/55555";
		excute(context, uiCb, abResultCallBack, url);
	}
}
