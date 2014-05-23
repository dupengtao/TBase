package com.dpt.tbase.app.base.interfaces;

/**
 * 定义fragment通信行为
 * 
 * @author dupengtao@cyou-inc.com
 *         2014-3-21
 */
public interface IFraCommCB {

    /** 0 成功 */
    int STATE_SUCCESS = 0;
    /** 1 没有网络 */
    int STATE_NO_NETWORK = 1;
    /** 2 列表为空 */
    int STATE_LIST_TEMPTY = 2;
    /** 3.加载失败 */
    int STATE_LOAD_FAILURE = 3;
    /** 4.loading */
    int STATE_LOADINGT = 4;
    
    
    /**
     * 对不同的异常展示不同样式
     * 
     * @param 1 没有网络,2 列表为空,3.加载失败,4.loading
     */
    void onErrorStateListener(int state);


    /**
     * 数据加载开始时切换样式
     * 
     * @param 4.loading
     */
    
    void onStartListener(int state);


}
