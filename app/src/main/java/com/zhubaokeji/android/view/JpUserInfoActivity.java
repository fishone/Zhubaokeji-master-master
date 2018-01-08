package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpUser;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.databinding.ZhubaoInfoBinding;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by Yuizhi on 2016/12/13.
 */

public class JpUserInfoActivity extends BaseActivity{
    @BindView(R.id.re_intercalate)
    RelativeLayout reIntercalate;
    @BindView(R.id.re_Company)
    RelativeLayout reCompany;
    @BindView(R.id.re_dropOut)
    RelativeLayout reDropOut;
    JpUserInfo userinfo = null;
    private Activity mContext;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZhubaoInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.zhubao_info);
        ButterKnife.bind(this);
        mContext=this;
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            userinfo = new JpUserInfo();
            userinfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.setJpUser(userinfo);

        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_info_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("我的JP");
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
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    @OnClick({R.id.re_Company, R.id.re_dropOut,R.id.re_intercalate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_Company:
                startActivity(new Intent(JpUserInfoActivity.this, CompanyActivity.class));
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                break;
            case R.id.re_dropOut:
                jp_Login_Boolean = false;
                Intent loginOut_intent = new Intent();
                loginOut_intent.setClass(JpUserInfoActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                loginOut_intent.putExtra("BottomNavigationBar","1");
                startActivity(loginOut_intent);
                JpUser user = new JpUser();
                preferencesUtil=new SharedPreferencesUtil(getApplicationContext());
                try {
                    user = preferencesUtil.getJpUsertInfo();
                } catch (Exception e) {
                    Logger.e("Jp退出",e);
                }
                user.setVippsd("");
                preferencesUtil.saveJpUserInfo(user);
                break;
            case R.id.re_intercalate:
                startActivity(new Intent(JpUserInfoActivity.this, BasicSettingsActivity.class));
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                break;
        }
    }
}
