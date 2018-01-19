package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpUser;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.SpacingTextView;
import com.zhubaokeji.android.utils.StringUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.XEditText;
import com.zhubaokeji.library.TitleBar;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by Yuizhi on 2016/12/12.
 */

public class JpLoginActivity extends BaseActivity {
    @BindView(R.id.username)
    XEditText username;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.login)
    SpacingTextView login;
    protected static final int SUCCESS = 1;
    protected static final int ERROR = 0;
    @BindView(R.id.NetworkTips)
    TextView NetworkTips;
    @BindView(R.id.relative_Tips)
    RelativeLayout relativeTips;
    private Activity mContext;
    @BindView(R.id.jp_retrievePassword)
    TextView jpRetrievePassword;
    LoadingDialog dialog; //新建一个ProgressDialog
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    JpUser user = new JpUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_login);
        ButterKnife.bind(this);
        /*设置标题*/
        boolean isImmersive = true;
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_jp_login_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setTitle("JP会员登录");
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetUtil.isConnected(mContext) && jp_Login_Boolean){
                    finish();
                }else {
                    Intent login_intent = new Intent();
                    login_intent.setClass(JpLoginActivity.this, MainActivity.class);
                    login_intent.putExtra("BottomNavigationBar", "1");
                    startActivity(login_intent);
                }
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

            }
        });

        initTranslucentStatusBar();
        mContext = this;
        if (NetUtil.isJpQuery(mContext) != true) {
            relativeTips.setVisibility(View.VISIBLE);
        } else {
            relativeTips.setVisibility(View.GONE);
        }
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            user = preferencesUtil.getJpUsertInfo();
            username.setText(user.getVipid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            relativeTips.setVisibility(View.VISIBLE);
        }else {
            relativeTips.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }


    /**
     * 登陆
     *
     * @param user
     */
    private void login(final JpUser user) {
        final String path = Urls.URL + Urls.JPLOGINURL + "&vipid=" + user.getVipid() + "&vippsd=" + user.getVippsd();
        OkGo.<LzyResponse<JpUserInfo>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<JpUserInfo>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<JpUserInfo>> response) {
                        try {
                            JpUserInfo userInfo = new JpUserInfo();
                            Gson gson = new Gson();
                            if (null != response && null !=response.body()) {
                                LzyResponse<JpUserInfo> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                dialog.close();
                                switch (lzyResponse.status) {
                                    case 1:
                                        userInfo=lzyResponse.getMsgdata();
                                        if (userInfo != null && userInfo.getToken() != null) {
                                            jp_Login_Boolean = true;
                                            preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
                                            preferencesUtil.saveJpInfo(userInfo);
                                            preferencesUtil.saveJpUserInfo(user);
                                            Intent login_intent = new Intent();
                                            login_intent.setClass(JpLoginActivity.this, MainActivity.class);
                                            login_intent.putExtra("BottomNavigationBar", "1");
                                            startActivity(login_intent);
                                        }
                                        break;
                                    default:
                                        ToastUtil.show(mContext, message);
                                }
                            }
                        }catch (Exception e) {
                            dialog.close();
                            ToastUtil.show(mContext, "登录失败");
                            Logger.e("zhubao登录", e);
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse<JpUserInfo>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        Throwable throwable=response.getException();
                        if(throwable !=null)throwable.printStackTrace();
                        if(throwable instanceof UnknownHostException ||throwable instanceof ConnectException){
                            ToastUtil.show(mContext,"网络未连接,请连接网络");
                        }else if(throwable instanceof SocketTimeoutException){
                            ToastUtil.show(mContext,"网络请求超时");
                        }else {
                            ToastUtil.show(mContext, "登录失败");
                        }
                        dialog.close();
                    }
                });

    }

    @OnClick({R.id.login, R.id.jp_retrievePassword,R.id.relative_Tips,R.id.setting_network})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:  //登陆
                user.setVipid(username.getText().toString());
                user.setVippsd(password.getText().toString());
                if (StringUtil.isValidString(user.getVipid()) && StringUtil.isValidString(user.getVippsd())) {
                    if (NetUtil.isConnected(mContext) != true) {
                        ToastUtil.show(mContext, "网络未连接,请检查网络后重试");
                        break;
                    }
                    dialog = new LoadingDialog(this, "登录中...");
                    dialog.show();
                    login(user);
                } else {
                    ToastUtil.show(mContext, "请填写密码与用户名");
                }
                break;
            case R.id.jp_retrievePassword:
                Intent result_intent = new Intent();
                result_intent.setClass(JpLoginActivity.this, JpVerifyAccountActivity.class);
                startActivity(result_intent);
                break;
            case R.id.setting_network:case R.id.relative_Tips:
                NetUtil.openSetting(mContext);
                break;
        }
    }
}
