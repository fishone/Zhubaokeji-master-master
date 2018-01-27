package com.zhubaokeji.android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.zhubaokeji.android.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by cheng on 2016/11/28.
 */
public  class NetBroadcastReceiver extends BroadcastReceiver {

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//            int netWorkState = NetUtil.getNetType(context);
//            // 接口回调传过去状态的类型
//            if(evevt !=null){
//                evevt.onNetChange(netWorkState);
//            }
//        }
//    }

    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.zhanyun.api.netstatus.CONNECTIVITY_CHANGE";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final static String TAG = NetBroadcastReceiver.class.getSimpleName();

    private static boolean isNetAvailable = false;
    private static NetUtil.NetType mNetType;
    private static ArrayList<NetEvevt> mNetChangeObservers;
    private static BroadcastReceiver mBroadcastReceiver;

    private static BroadcastReceiver getReceiver() {
        if (null == mBroadcastReceiver) {
            synchronized (NetBroadcastReceiver.class) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = new NetBroadcastReceiver();
                }
            }
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetBroadcastReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION)) {
            if (!NetUtil.isConnected(context)) {
                Log.e(this.getClass().getName(), "<--- network disconnected --->");
                isNetAvailable = false;
                mNetType = NetUtil.getNetType(context);
            } else {
                Log.e(this.getClass().getName(), "<--- network connected --->");
                isNetAvailable = true;
                mNetType = NetUtil.getNetType(context);
            }
            notifyObserver();
        }
    }


    /**
     * 注册
     *
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 清除
     *
     * @param mContext
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 反注册
     *
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (mBroadcastReceiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (Exception e) {

            }
        }

    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static NetUtil.NetType getAPNType() {
        return mNetType;
    }

    private void notifyObserver() {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetEvevt observer = mNetChangeObservers.get(i);
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetConnected(NetUtil.NetType.NONE);
                    }
                }
            }
        }
    }

    /**
     * 添加网络监听
     *
     * @param observer
     */
    public static void registerObserver(NetEvevt observer) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = new ArrayList<NetEvevt>();
        }
        mNetChangeObservers.add(observer);
    }

    /**
     * 移除网络监听
     *
     * @param observer
     */
    public static void removeRegisterObserver(NetEvevt observer) {
        if (mNetChangeObservers != null) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }


    // 自定义接口
    public interface NetEvevt {
        /**
         * 网络连接回调 type为网络类型
         */
        void onNetConnected(NetUtil.NetType type);
    }
}
