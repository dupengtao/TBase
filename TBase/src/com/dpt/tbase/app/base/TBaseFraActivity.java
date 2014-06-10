package com.dpt.tbase.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.dpt.tbase.app.base.interfaces.IFraCommCB;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.base.utils.TFraLruCache;
import com.dpt.tbase.app.base.utils.TFragmentFactory;
import com.dpt.tbase.app.fragment.AbCompatibleFragment;
import com.dpt.tbase.app.fragment.AbCompatibleFragment.CompatibleListener;
import com.dpt.tbase.app.fragment.TBaseFragment;

/**
 * BaseFragmentActivity
 * 
 * 
 * @author dupengtao@cyou-inc.com 2014-3-20
 */
public abstract class TBaseFraActivity extends FragmentActivity implements
        AbCompatibleFragment.CompatibleListener, IFraCommCB {

    private static final String TAG = "TBaseFraActivity";
    protected TBaseFragment mCur, mPre;
    protected AbCompatibleFragment mCompatibleFra;
    protected boolean mSingleFragment;
    private int mMaxCacheSize = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TFragmentFactory.initFractory(getMaxSize(), getSupportFragmentManager());
        addFirstContent();
    }

    /**
     * maxSize for caches
     * 
     * @return maxSize
     */
    protected int getMaxSize() {
        return mMaxCacheSize;
    }

    /**
     * when Activity onResume() will be called ,if current frgament is compatibleFragment ,
     * it not execute callback .
     * 
     * @see com.dpt.tbase.app.base.interfaces.ICustomFragmentListener#onActivityResumedLoad(int)
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isCurCompatible()) {
            mPre.onActivityResumedLoad(1);
        } else {
            mCur.onActivityResumedLoad(1);
        }
    }

    private void addFirstContent() {
        TBaseFragment to = getInitContent();
        if (to != null) {
            switchContent(null, to);
        } else {
            throw new NullPointerException("The init fragment is null");
        }
    }

    /**
     * return the init {@link TBaseFragment}
     * 
     * @see #addFirstContent()
     */
    public abstract TBaseFragment getInitContent();

    /**
     * change cur frgment to pre fragment
     */
    protected void switchContentBack() {

        if (mPre == null) {
            // TODO 完蛋了? 相邻的2个fra不会为空吧
        }

        if (mPre != mCur && mPre != mCompatibleFra) {
            switchContent(mCur, mPre);
        }
    }

    // TODO 如果targetFra 为 null 要做处理 看看是否可用工厂设计模式
    // switchContent(TBaseFragment fromFra, TBaseFragment targetFra) 中的targetFra 要在调用这个方法前 确定不能空
    /**
     * change fromFragment to toFragment and add {@link TFragmentFactory},{@link TFraLruCache}
     * 
     * @param fromFra usually is cur fragment
     * @param targetFra
     * @return
     *         cur fragement equals to targetFragmet return false
     *         commitAllowingStateLoss throw exception return false
     *         other cases return true
     */
    protected boolean switchContent(TBaseFragment fromFra, TBaseFragment targetFra) {
        if (targetFra == null) {
            throw new NullPointerException("The init fragment is null");
        }
        if (mCur != targetFra) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            int[] animations = getCustomAnimationsResId();
            if (animations != null && animations.length >= 2) {
                transaction.setCustomAnimations(animations[0], animations[1]);
            }
            if (!targetFra.isAdded()) { // if not added
                if (fromFra != null) {
                    transaction.hide(fromFra);// hide cur fragment
                    mPre = fromFra;
                }
                mCur = targetFra;

                try {
                    transaction.add(getReplaceLayoutResId(), targetFra).commitAllowingStateLoss(); // add and show toFra
                    if (targetFra.isAddCache()) {
                        TFragmentFactory.getInstance().putAndAddCache(targetFra.getKey(),
                                targetFra.getClass());
                    } 
                    TFragmentFactory.getInstance().put(targetFra.getKey(), targetFra.getClass());
                    // mTFraCache.put(targetFra.getKey(), targetFra);
                    // TFragmentFactory.fraKeys.put(targetFra.getKey(), targetFra.getClass());
                } catch (Exception e) {
                    LogHelper.e(TAG, "switchContent error", e);
                    return false;
                }
            } else {
                if (fromFra != null) {
                    transaction.hide(fromFra);// hide cur fragment
                    mPre = fromFra;
                }
                mCur = targetFra;
                try {
                    transaction.show(targetFra).commitAllowingStateLoss(); // show toFra
                    TFragmentFactory.getInstance().refreshLruCache(targetFra.getKey(), targetFra);
                } catch (Exception e) {
                    LogHelper.e(TAG, "switchContent error", e);
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * show no work fragment
     * 
     * @see IFraCommCB#STATE_SUCCESS
     * @see IFraCommCB#STATE_NO_NETWORK
     * @see IFraCommCB#STATE_LIST_TEMPTY
     * @see IFraCommCB#STATE_LOAD_FAILURE
     * @see IFraCommCB#STATE_LOADINGT
     */
    private void showCompatibleFra(int state) {
        if (mCompatibleFra == null) {
            mCompatibleFra = getCompatibleFragment();
        }
        if (mPre != mCur && mCur != mCompatibleFra) {
            // TODO 下面代码貌似不需要
            // mPre = mCur;
            switchContent(mCur, mCompatibleFra);
            mCompatibleFra.changeStyleByState(state);
        } else if (mSingleFragment && mCur != mCompatibleFra) {// TODO
                                                               // 这个判断貌似没有用看看是否能删除
            switchContent(mCur, mCompatibleFra);
            mCompatibleFra.changeStyleByState(state);
        } else if (mCur == mCompatibleFra) {
            if (IFraCommCB.STATE_SUCCESS == state) {
                switchContentBack();
            }
            mCompatibleFra.changeStyleByState(state);
            mCompatibleFra.refreshComplete();
        }

    }

    /**
     * @see CompatibleListener#onTryAgain()
     */
    @Override
    public void onTryAgain() {
        mPre.onExceptionReLoad();
    }

    private boolean isCurCompatible() {
        if (mCompatibleFra != null && mCur != null) {
            return mCompatibleFra == mCur;
        } else {
            return false;
        }
    }

    /**
     * @see FragmentTransaction#add(int, android.support.v4.app.Fragment)
     * @return replaceLayoutResId
     */
    public abstract int getReplaceLayoutResId();

    @Override
    public void onErrorStateListener(int state) {
        showCompatibleFra(state);
    }


    @Override
    public void onStartListener(int state) {
        showCompatibleFra(state);
    }
 

    /**
     * @see FragmentTransaction#setCustomAnimations(int, int)
     * @return Animations in xml eg.R.anim.xxx
     */
    protected int[] getCustomAnimationsResId() {
        return null;
    }

    /**
     * get CompatibleFragment instance
     * 
     * @return
     */
    public abstract AbCompatibleFragment getCompatibleFragment();

}
