package com.dpt.tbase.app.fragment;
import com.dpt.tbase.app.base.interfaces.ICustomFragmentListener;
import com.dpt.tbase.app.base.utils.TFraLruCache;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * 
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-17
 */
public abstract class TBaseFragment extends Fragment implements ICustomFragmentListener{

    protected Resources mRes; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRes = getResources();
    }
      
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedLoad(0);
    }
    
    public abstract String getKey();
    /**
     * 
     * @return true add in {@link TFraLruCache}
     */
    public boolean isAddCache() {
        return true;
    }

}
