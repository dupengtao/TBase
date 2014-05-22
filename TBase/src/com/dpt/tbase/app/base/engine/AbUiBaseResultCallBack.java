/**
 * 
 */
package com.dpt.tbase.app.base.engine;

import com.dpt.tbase.app.base.interfaces.IFraCommCB;

/**
 * @author dupengtao@cyou-inc.com
 *         2014-3-23
 */
public abstract class AbUiBaseResultCallBack<T> implements IUiBaseResultCallBack<T> {

    private IFraCommCB commCallBack;

    public AbUiBaseResultCallBack() {
        super();
    }

    public AbUiBaseResultCallBack(IFraCommCB commCallBack) {
        super();
        this.commCallBack = commCallBack;
    }

    @Override
    public void onFailureResult(Throwable e, String content) {
        if (commCallBack != null) {
            commCallBack.onErrorStateListener(IFraCommCB.STATE_LOAD_FAILURE);
        }
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void noNetworkEnvironment() {
        if (commCallBack != null) {
            commCallBack.onErrorStateListener(IFraCommCB.STATE_NO_NETWORK);
        }
    }

}
