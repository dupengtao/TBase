package com.dpt.tbase.app.base.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dpt.tbase.app.fragment.TBaseFragment;

public class TFragmentFactory {
	
	private static final String TAG=TFragmentFactory.class.getSimpleName();
	
    private static final Map<String, Class<? extends TBaseFragment>> fraKeys = new HashMap<String, Class<? extends TBaseFragment>>();

    private static TFragmentFactory mInstance;

    private TFraLruCache mTFraCache;

    private TFragmentFactory(int maxSize, FragmentManager fragmentManager) {
        mTFraCache = new TFraLruCache(maxSize, fragmentManager);
    }

    public static synchronized TFragmentFactory initFractory(int maxSize, FragmentManager fragmentManager) {
        if (mInstance == null) {
            mInstance = new TFragmentFactory(maxSize, fragmentManager);
        }
        return mInstance;
    }

    public static TFragmentFactory getInstance() {
        return mInstance;
    }

    private TBaseFragment createFragment(String fraKey) {
        return createFragment(fraKeys.get(fraKey));
    }

    private TBaseFragment createFragment(Class<? extends TBaseFragment> fragmentClass) {
        Constructor<?> constructor;
        TBaseFragment fragment = null;
        if (fragmentClass != null) {
            try {
                constructor = fragmentClass.getConstructor(new Class[0]);
                fragment = (TBaseFragment) constructor.newInstance(new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    /**
     * if true {@link #createFragment(Class)} return newInstance else {@link TFragmentFactory#createFragment(Class)} throw exception
     * @param fraKey {@link TBaseFragment#getKey()}
     */
    public TBaseFragment putAndAddCache(String fraKey, Class<? extends TBaseFragment> clazz) {
    	put(fraKey, clazz);
        TBaseFragment tfra = createFragment(clazz);
        if (tfra != null) {
            mTFraCache.put(fraKey, tfra);
        } 
        return tfra;
    }

    public void put(String fraKey, Class<? extends TBaseFragment> clazz) {
        fraKeys.put(fraKey, clazz);
    }

    public void refreshLruCache(String fraKey, TBaseFragment fragment) {
        if (fragment.isAddCache()) {
            mTFraCache.get(fraKey);
        }
    }

    public TBaseFragment get(String fraKey) {
        Fragment fragment = mTFraCache.get(fraKey);
        if (fragment == null) {
            fragment = createFragment(fraKey);
        }
        return (TBaseFragment) fragment;
    }
    
    public boolean isAdd(String key){
    	return fraKeys.get(key)!=null;
    }
}
