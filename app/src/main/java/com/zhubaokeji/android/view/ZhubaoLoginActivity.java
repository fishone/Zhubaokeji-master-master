package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.lzy.okgo.model.Response;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2016/11/25.
 */

public class ZhubaoLoginActivity extends BaseActivity {

    @BindView(R.id.username)
    XEditText username;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.zhubao_register)
    TextView zhubaoRegister;
    LoadingDialog dialog; //新建一个ProgressDialog
    @BindView(R.id.login)
    SpacingTextView login;
    @BindView(R.id.relative_Tips)
    RelativeLayout relativeTips;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;
    @BindView(R.id.setting_network)
    Button settingNetwork;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    JpUser user = new JpUser();
    private Activity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_login);
        ButterKnife.bind(this);
        mContext = this;
        /*设置标题*/
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_login_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));

        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("我的助宝");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetUtil.isConnected(mContext) && zhubao_Login_boolean){
                    finish();
                }else {
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                }
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });

        login.setLetterSpacing(5);

        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            user = preferencesUtil.getZhubaoUsertInfo();
            username.setText(user.getVipid());
//            jpUser=preferencesUtil.getJpUsertInfo();

            BasicSetting basicSetting = preferencesUtil.getBasicSetting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (NetUtil.isZhubaoQuery(mContext) != true) {
            relativeTips.setVisibility(View.VISIBLE);
        } else {
            relativeTips.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == -1) {
            jp_Login_Boolean = false;
            relativeTips.setVisibility(View.VISIBLE);
        } else {
            relativeTips.setVisibility(View.GONE);
        }
    }

    /**
     * 登陆
     *
     * @param user
     */
    private void login(final JpUser user) {
        dialog.show();
        final String path = Urls.ZHUBAOURL + Urls.ZHUBAOLOGINURL + user.toJson();
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
                                dialog.close();
                                switch (lzyResponse.status) {
                                    case 1:
                                        userInfo=lzyResponse.getMsgdata();
                                        if (userInfo != null && userInfo.getToken() != null) {
                                            zhubao_Login_boolean = true;
                                            preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
                                            preferencesUtil.saveInfo(userInfo);
                                            preferencesUtil.saveZhubaoUserInfo(user);
                                            Intent login_intent = new Intent();
                                            login_intent.setClass(ZhubaoLoginActivity.this, MainActivity.class);
                                            overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                                            startActivity(login_intent);
                                            ToastUtil.show(ZhubaoLoginActivity.this, "登录成功");
                                        }
                                        break;
                                    default:
                                        ToastUtil.show(mContext, message);
                                        break;
                                }
                            }else {
                                dialog.close();
                                ToastUtil.show(mContext,"登录失败");
                            }
                        } catch (Exception e) {
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
                        }
                        dialog.close();
                    }
                });
    }

    @OnClick({R.id.zhubao_register, R.id.login, R.id.relative_Tips,R.id.setting_network})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                user.setVipid(username.getText().toString());
                user.setVippsd(password.getText().toString());
                if (StringUtil.isValidString(user.getVipid()) && StringUtil.isValidString(user.getVippsd())) {
//                    if (NetUtil.isConnected(mContext) != true) {
//                        ToastUtil.show(ZhubaoLoginActivity.this, "网络未连接,请检查网络后重试");
//                        break;
//                    }
                    dialog = new LoadingDialog(this, "登录中...");
                    login(user);
                    break;
                } else {
                    ToastUtil.show(ZhubaoLoginActivity.this, "请填写密码与用户名");
                    break;
                }
            case R.id.zhubao_register:
                Intent intent = new Intent();
                intent.setClass(ZhubaoLoginActivity.this, ZhubaoRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.relative_Tips: case R.id.setting_network:
                NetUtil.openSetting(mContext);
                break;
        }
    }
}
