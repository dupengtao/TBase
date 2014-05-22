package com.dpt.tbase.app.base.engine.interfaces;

import com.dpt.tbase.app.base.engine.IUiBaseResultCallBack;


/**
 * 对列表样式进行封装
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-18
 */
public interface IListStyleEngine extends ICustomStyleEngine{
     void pullDownToRefresh(IUiBaseResultCallBack<?> resultCallBack);
     void pullUpToLoad(IUiBaseResultCallBack<?> resultCallBack);
}
