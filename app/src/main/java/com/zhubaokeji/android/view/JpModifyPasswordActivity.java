package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ResetPassword;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.TimeCount;
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
 * Created by fisho on 2017/6/22.
 */

public class JpModifyPasswordActivity extends BaseActivity {
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.identifyingCode)
    XEditText identifyingCode;
    @BindView(R.id.gain)
    TextView gain;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.repeatPassword)
    XEditText repeatPassword;
    @BindView(R.id.confirm)
    TextView confirm;
    JpUserInfo jpUserInfo;
    ResetPassword resetPassword;
    TimeCount time;
    private Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_modifypassword);
        ButterKnife.bind(this);
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_modifyPassword_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("找回密码");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
        mContext=this;
        jpUserInfo = getIntent().getParcelableExtra("jpUserInfo");
        userName.setText(jpUserInfo.getVipaccount());
        phoneNumber.setText(jpUserInfo.getTellphone());
        time = new TimeCount(60000, 1000,gain);
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick({R.id.gain, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gain:
                time.start();
                String path= Urls.URL + Urls.JPGETCHECKCODE+"&vipaccount="+ jpUserInfo.getVipaccount();
                OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                        .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                            @Override
                            public void onSuccess(Response<LzyResponse> response) {
                                try {
                                    if (null !=response && null !=response.body()){
                                        LzyResponse lzyResponse=response.body();
                                        String message =lzyResponse.message;
                                        switch (lzyResponse.status){
                                            case 1:
                                                break;
                                            default:
                                                ToastUtil.show(mContext,message);
                                                break;
                                        }
                                    }
                                }catch (Exception e){
                                    Logger.e("jp 获取验证码",e);
                                }
                            }
                            @Override
                            public void onError(Response<LzyResponse> response) {
                                //网络请求失败的回调,一般会弹个Toast
                                NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                            }
                        });
                break;
            case R.id.confirm:
                if(identifyingCode.getText().toString().equals("")){
                   ToastUtil.show(getApplicationContext(),"请输入验证码");
                    break;
                }
                if(password.getText().toString().equals("") ||repeatPassword.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"请输入密码");
                    break;
                }
                if(!password.getText().toString().equals(repeatPassword.getText().toString())){
                    ToastUtil.show(getApplicationContext(),"两次密码不一致");
                    break;
                }
                resetPassword=new ResetPassword();
                resetPassword.setVipaccount(userName.getText().toString());
                resetPassword.setVippsd(password.getText().toString());
                resetPassword.setCheckcode(identifyingCode.getText().toString());
                String reviseUrl= Urls.URL + Urls.JPRESETPASSWORD+resetPassword.toJson();
                OkGo.<LzyResponse>get(reviseUrl)     // 请求方式和请求url
                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                        .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                            @Override
                            public void onSuccess(Response<LzyResponse> response) {
                                try {
                                    if(null != response && null !=response.body()){
                                        LzyResponse lzyResponse=response.body();
                                        String message = lzyResponse.message;
                                        switch (lzyResponse.status){
                                            case 1:
                                                Intent result_intent = new Intent();
                                                result_intent.setClass(JpModifyPasswordActivity.this, JpLoginActivity.class);
                                                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                                                startActivity(result_intent);
                                                ToastUtil.show(mContext,message);
                                                break;
                                            default:
                                                ToastUtil.show(mContext,message);
                                                break;
                                        }
                                    }
                                }catch (Exception e){
                                    Logger.e("JP修改密码",e);
                                }
                            }
                            @Override
                            public void onError(Response<LzyResponse> response) {
                                //网络请求失败的回调,一般会弹个Toast
                                NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                            }
                        });
                break;
        }
    }
}
