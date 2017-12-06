package com.zhubaokeji.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * Created by fisho on 2017/4/26.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
//    public boolean isFirst = true;
    int freshen = 10001;

    //--------------------system method callback------------------------//

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initPrepare();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            lazyLoad();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if(mRootView == null){
//            mRootView = initView(inflater,container,savedInstanceState);
//        }
//
//        return mRootView;
//    }

    //--------------------------------method---------------------------//

    /**
     * 懒加载
     */
    protected void lazyLoad(){
        if(!isPrepared || !isVisible){
            return;
        }
        initData(freshen);
//        isFirst = false;
    }

    //--------------------------abstract method------------------------//

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    protected abstract void initPrepare();

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 这里获取数据，刷新界面
     * @param freshen
     */
    protected abstract void initData(int freshen);

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
//    protected abstract View initView(LayoutInflater inflater,
//                                     @Nullable ViewGroup container,
//                                     @Nullable Bundle savedInstanceState);
//    protected View mRootView;
//    public Context mContext;
//    protected boolean isVisible;
//    private boolean isPrepared;
//    public boolean isFirst = true;
//    int freshen = 10001;
//
//    public BaseFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
////        Log.d("TAG", "fragment->setUserVisibleHint");
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            lazyLoad();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getActivity();
//        setHasOptionsMenu(true);
////        Log.d("TAG", "fragment->onCreate");
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        if (mRootView == null) {
//            mRootView = initView();
//        }
////        Log.d("TAG", "fragment->onCreateView");
//        return mRootView;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        Log.d("TAG", "fragment->onActivityCreated");
//        isPrepared = true;
//        lazyLoad();
//    }
//
//    protected void lazyLoad() {
//        if (!isPrepared || !isVisible || !isFirst) {
//            return;
//        }
//        Logger.d("TAG", getClass().getName() + "->initData()");
//        initData(freshen);
//        isFirst = false;
//    }
//
//    //do something
//    protected void onInvisible() {
//    }
//    public abstract View initView();
//
//    public abstract void initData(int freshen);
}

