package com.dpt.tbase.app.base.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;

public class TFraLruCache extends LruCache<String, Fragment>{
    private FragmentManager mFraManager;
    private static final String TAG="TFraLruCache";
    public TFraLruCache(int maxSize,FragmentManager fragmentManager) {
        super(maxSize);
        mFraManager=fragmentManager;
    }
    @Override
    protected int sizeOf(String key, Fragment value) {
        // TODO Auto-generated method stub
        return super.sizeOf(key, value);
    }
    /**
     * Called for entries that have been evicted or removed. This method is
     * invoked when a value is evicted to make space, removed by a call to
     * {@link #remove}, or replaced by a call to {@link #put}. The default
     * implementation does nothing.
     *
     * <p>The method is called without synchronization: other threads may
     * access the cache while this method is executing.
     *
     * @param evicted true if the entry is being removed to make space, false
     *     if the removal was caused by a {@link #put} or {@link #remove}.
     * @param newValue the new value for {@code key}, if it exists. If non-null,
     *     this removal was caused by a {@link #put}. Otherwise it was caused by
     *     an eviction or a {@link #remove}.
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Fragment oldValue, Fragment newValue) {
        
        if (evicted) {
            mFraManager.beginTransaction().remove(oldValue).commitAllowingStateLoss();
        }
        
        LogHelper.d(TAG, "oldValue is null ?" +(oldValue==null));
        
    }
        
}
