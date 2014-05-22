package com.dpt.tbase.app.base.interfaces;

/**
 * 定义baseFragment操作行为
 * 
 * @author dupengtao@cyou-inc.com
 *
 * 2014-4-10
 */
public interface ICustomFragmentListener {

    /**
     * after fragment onViewCreated()
     * @param state
     */
    void onViewCreatedLoad(int state);

    /**
     * after activity onResume()
     * @param state
     */
    void onActivityResumedLoad(int state);

    /**
     * 判断是否为双屏
     * 
     * @return true=是
     */
    boolean isTwoPane();
    
    /**
     * 异常时重新加载
     */
    void onExceptionReLoad();

}