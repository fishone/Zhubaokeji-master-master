package com.zhubaokeji.android.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.bean.JpUser;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.base.BaseActivity;
//import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.StringUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import com.lzy.okgo.model.Response;
import java.util.ArrayList;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

public class SplashActivity extends BaseActivity{
    public static final String TAG_EXIT = "exit";
    protected static final int SUCCESS = 0;
    public static boolean update=true;
    private Activity mContext;
    JpUser user=new JpUser();
    JpUser jpUser=new JpUser();
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    public static Runnable runnable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        initTranslucentStatusBar();
        handler.sendEmptyMessageDelayed(0,3000);
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());

        try {
            user=preferencesUtil.getZhubaoUsertInfo();
            jpUser=preferencesUtil.getJpUsertInfo();
           BasicSetting basicSetting = preferencesUtil.getBasicSetting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtil.isValidString(user.getVipid())){
            login(user);
        }
        if (StringUtil.isValidString(jpUser.getVipid())){
            Jplogin(jpUser);
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
    }

    /**
     * 助宝登陆
     * @param user
     */
    private void login(final JpUser user) {
        if(NetUtil.isConnected(mContext) != true) {
            zhubao_Login_boolean=false;
           return;
        }
        final String path = Urls.ZHUBAOURL+ Urls.ZHUBAOLOGINURL+ user.toJson();
        OkGo.<LzyResponse<JpUserInfo>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<JpUserInfo>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<JpUserInfo>> response) {
                        try {
                            JpUserInfo userInfo = new JpUserInfo();
                            if (null != response && response.body() !=null) {
                                LzyResponse<JpUserInfo> lzyResponse =response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        userInfo=lzyResponse.getMsgdata();
                                        if (userInfo != null && userInfo.getToken() != null) {
                                            zhubao_Login_boolean = true;
                                            preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
                                            preferencesUtil.saveInfo(userInfo);
                                            preferencesUtil.saveZhubaoUserInfo(user);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e("zhubao登录", e);
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse<JpUserInfo>> response) {
                    }
                });
    }
    /**
     * JP登陆
     * @param user
     */
    private void Jplogin(final JpUser user) {
        if(NetUtil.isConnected(mContext) != true) {
            jp_Login_Boolean=false;
            return;
        }
        final String path = Urls.URL+ Urls.JPLOGINURL + "&vipid=" + user.getVipid() + "&vippsd=" + user.getVippsd();
        OkGo.<LzyResponse<JpUserInfo>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<JpUserInfo>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<JpUserInfo>> response) {
                        try {
                            if(response !=null && response.body() !=null){
                                LzyResponse<JpUserInfo> jpUserInfoResponse =response.body();
                                String message=jpUserInfoResponse.getMessage();
                                JpUserInfo userInfo=new JpUserInfo();
                                switch (jpUserInfoResponse.status){
                                    case 1:
                                        userInfo=jpUserInfoResponse.getMsgdata();
                                        if (userInfo != null && userInfo.getToken() != null) {
                                            jp_Login_Boolean = true;
                                            preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
                                            preferencesUtil.saveJpInfo(userInfo);
                                            preferencesUtil.saveJpUserInfo(user);
                                        }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse<JpUserInfo>> response) {
                    }
                });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
