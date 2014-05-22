package com.dpt.tbase.app.fragment;

import com.dpt.tbase.app.base.interfaces.IFraCommCB;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 容错页面
 * 
 * @author dupengtao@cyou-inc.com
 *         2014-4-10
 */
public abstract class AbCompatibleFragment extends TBaseFragment {

    public interface CompatibleListener {
        /**
         * when cur fragment is {@link AbCompatibleFragment} by any no work state
         * the {@link AbCompatibleFragment#tryAgain()} method will be call this
         * callback method
         * @see IFraCommCB#STATE_NO_NETWORK
         * @see IFraCommCB#STATE_LIST_TEMPTY
         * @see IFraCommCB#STATE_LOAD_FAILURE
         * @see IFraCommCB#STATE_LOADINGT
         */
        void onTryAgain();
    }

    private CompatibleListener mCallBack;
    protected View mView;

    @Override
    public void onViewCreatedLoad(int state) {
    }

    @Override
    public void onActivityResumedLoad(int state) {
    }

    @Override
    public boolean isTwoPane() {
        return false;
    }

    @Override
    public void onExceptionReLoad() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (CompatibleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SceneFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(abGetCreatViewLayoutRes(), container, false);
        return mView;
    }

    public View getCompatibleView() {
        return mView;
    }

    protected View findViewById(int id) {
        return mView.findViewById(id);
    }

    protected void tryAgain() {
        mCallBack.onTryAgain();
    }

    /**
     * R.layout.xxx
     * 
     * @return
     */
    public abstract int abGetCreatViewLayoutRes();

    /**
     * show no work fragment
     * 
     * @see IFraCommCB#STATE_SUCCESS
     * @see IFraCommCB#STATE_NO_NETWORK
     * @see IFraCommCB#STATE_LIST_TEMPTY
     * @see IFraCommCB#STATE_LOAD_FAILURE
     * @see IFraCommCB#STATE_LOADINGT
     */
    public abstract void changeStyleByState(int state);

    /**
     * finsh refreshComplete
     */
    public abstract void refreshComplete();

}
