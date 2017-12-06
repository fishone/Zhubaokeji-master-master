package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.UserRegisterRequest;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.XEditText;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.lzy.okgo.model.Response;

/**
 * Created by Yuizhi on 2017/1/13.
 */

public class ZhubaoRegisterActivity extends BaseActivity implements View.OnClickListener {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    HashMap<String, String> registerMap;
    UserRegisterRequest register = new UserRegisterRequest();
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    LoadingDialog dialog; //新建一个ProgressDialog
    private Activity mContext;
    @BindView(R.id.loginName)
    XEditText loginName;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.passwords)
    XEditText passwords;
    @BindView(R.id.mobilePhone)
    XEditText mobilePhone;
    @BindView(R.id.name)
    XEditText name;
    @BindView(R.id.company)
    XEditText company;
    @BindView(R.id.address)
    XEditText address;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuabo_register);
        ButterKnife.bind(this);
        mContext=this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_register_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setTitle("助宝注册");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    private void  validate(){
        register.setVipid(loginName.getText().toString());
        register.setVippsd(password.getText().toString());
        String psds = passwords.getText().toString();
        register.setTell(mobilePhone.getText().toString());
        register.setVipname(name.getText().toString());
        register.setCompany(company.getText().toString());
        register.setAddress(address.getText().toString());
        registerMap = new HashMap<String, String>();

        registerMap.put("registerStr", register.getVipid());
        registerMap.put("toastStr", "请输入账号");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", register.getVippsd());
        registerMap.put("toastStr", "请输入密码");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", psds);
        registerMap.put("toastStr", "请输入确认密码");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", register.getTell());
        registerMap.put("toastStr", "请输入手机号");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", register.getVipname());
        registerMap.put("toastStr", "请输入联系人");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", register.getCompany());
        registerMap.put("toastStr", "请输入公司名");
        list.add(registerMap);
        registerMap = new HashMap<String, String>();
        registerMap.put("registerStr", register.getAddress());
        registerMap.put("toastStr", "请输入公司地址");
        list.add(registerMap);
    }

    /**
     * 助宝注册
     *
     * @param register
     */
    private void registers(final UserRegisterRequest register) {
        final String path = Urls.ZHUBAOURL+ Urls.ZHUBAOREGISTERURL+register.toJson();
        OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                    @Override
                    public void onSuccess(Response <LzyResponse> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyResponse lzyResponse=response.body();
                                String message=lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        ToastUtil.show(mContext,message);
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(ZhubaoRegisterActivity.this, MainActivity.class);
                                        overridePendingTransition(R.anim.in_right, R.anim.out_lef);
                                        startActivity(login_intent);
                                        break;
                                   default:
                                       ToastUtil.show(mContext,message);
                                       break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response <LzyResponse> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.zbException(mContext,response.getException());
                    }
                });
    }


    @OnClick(R.id.btn_register)
    public void onClick(View view) {
        validate();
        switch (view.getId()){
            case R.id.btn_register:
                for (Map map : list) {
                    if (map.get("registerStr").equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), map.get("toastStr").toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        list = new ArrayList<Map<String, String>>();
                        return;
                    }
                }
                if (!register.getVippsd().equals(passwords.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                String regExp = "[1][358]\\d{9}";
                if (register.getTell().matches(regExp) == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "手机号格式不正确", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                dialog=new LoadingDialog(this,"注册中..."); //弹出
                registers(register);
        }
    }
}
