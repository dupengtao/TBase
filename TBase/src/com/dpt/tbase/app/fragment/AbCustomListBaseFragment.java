package com.dpt.tbase.app.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

/*import com.dpt.tbase.app.R;
import com.dpt.tbase.app.adapter.AbCustomBaseAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;*/

/**
 * 对list式fragment进行简单封装,考虑到适配大屏
 * 耦合了PullToRefreshListView
 * @author dupengtao@cyou-inc.com
 *         2014-3-14
 */
public abstract class AbCustomListBaseFragment extends TBaseFragment {
    
//    protected FragmentActivity mContext;
//    protected boolean mIsTwoPane;
//    private PullToRefreshListView mListView;
//    private AbCustomBaseAdapter<?> mCustomListAdapter;
//    protected Mode mDirection;
//    private RelativeLayout mRlclBootomArea,mRlclHeadArea;
//    private View mMainView;
//    private RelativeLayout mBacDim;
//    
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getActivity();
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mIsTwoPane = isTwoPane();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        mMainView = inflater.inflate(R.layout.fragment_customlist,
//                container, false);
//        mRlclHeadArea = (RelativeLayout) mMainView.findViewById(R.id.rlclHeadArea);
//        View headView = getHeadView(mRlclHeadArea);
//        if (headView != null) {
//            mRlclHeadArea.addView(headView);
//        }
//        mRlclBootomArea = (RelativeLayout) mMainView.findViewById(R.id.rlclBootomArea);
//        View bootomView = getBootomView(mRlclBootomArea);
//        if(bootomView!=null){
//            mRlclBootomArea.addView(bootomView);
//        }
//        if(mIsTwoPane){
//            initDetialFragment(R.id.details_layout);
//        }
//        
//        return mMainView;
//    }
//    /**
//     * 展示详情的布局id
//     * @param detailsLayoutRes
//     */
//    public abstract void initDetialFragment(int detailsLayoutRes);
//
//    /**
//     * 自定义顶部
//     * @param rlclHeadArea 
//     * 
//     * @return
//     */
//    public abstract View getHeadView(ViewGroup HeadArea);
//
//    /**
//     * 自定义底部
//     * @param rlclBootomArea 
//     * 
//     * @return
//     */
//    public abstract View getBootomView(ViewGroup bootomArea);
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initView();
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//    private void initView() {
//        mListView = (PullToRefreshListView) findViewById(R.id.prlCustList);
//        setListViewParameters();
//        mCustomListAdapter = getCustomListAdapter();
//        mListView.setAdapter(mCustomListAdapter);
//        mBacDim=(RelativeLayout)findViewById(R.id.bac_dim_layout);
//    }
//    protected View findViewById(int resId) {
//        return mMainView.findViewById(resId);
//    }
//    private void setListViewParameters() {
//        mListView.getLoadingLayoutProxy().setLoadingDrawable(
//                getResources().getDrawable(R.drawable.default_ptr_rotate));
//        mListView.setMode(Mode.BOTH);
//        
//        mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                updatedLabel(refreshView);
//                pullToRefresh(true);
//            }
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                updatedLabel(refreshView);
//                pullToRefresh(false);
//            }
//
//        });
//        
//    }
//    /**
//     * 刷新样式
//     * @param refreshView
//     */
//    public void updatedLabel(PullToRefreshBase<ListView> refreshView) {
//        String label = DateUtils.formatDateTime(mContext,
//                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
//                        | DateUtils.FORMAT_SHOW_DATE
//                        | DateUtils.FORMAT_ABBREV_ALL);
//        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//    }
//    
//    
//    public RelativeLayout getRlclBootomArea() {
//        return mRlclBootomArea;
//    }
//
//    public RelativeLayout getRlclHeadArea() {
//        return mRlclHeadArea;
//    }
//    /**
//     * 刷新操作
//     * @param direction true=up ; false=down
//     */
//    public abstract void pullToRefresh(boolean direction);
//
//    /**
//     * 加载样式
//     * 
//     * @param isLoad false=加载完成
//     */
//    protected void changeLoad(boolean isLoad) {
//        if (isLoad) {
//            mListView.setVisibility(View.VISIBLE);
//        } else {
//            mListView.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 刷新完成
//     */
//    protected void finishLoad() {
//        mCustomListAdapter.notifyDataSetChanged();
//        mListView.onRefreshComplete();
//    }
//    
//    /**
//     * 使布局变暗
//     * @param isVisibility
//     */
//    public void showBacDim(boolean isVisibility) {
//        int i =isVisibility ?View.VISIBLE:View.GONE;
//        mBacDim.setVisibility(i);
//    }
//
//    /**
//     * get PullToRefreshListView
//     * 
//     * @return
//     */
//    public PullToRefreshListView getListView() {
//        return mListView;
//    }
//
//
//    /**
//     * 传入listViewAdapter
//     * 
//     * @return
//     */
//    public abstract AbCustomBaseAdapter<?> getCustomListAdapter();
//
//    public abstract Class<?> getDoublePaneFragmentClazz();


}
