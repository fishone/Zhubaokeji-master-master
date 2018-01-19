package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpUser;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.databinding.ZhubaoInfoBinding;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2016/12/9.
 */

public class ZhubaoUserInfoActivity extends BaseActivity {
    @BindView(R.id.re_intercalate)
    RelativeLayout reIntercalate;
    @BindView(R.id.re_Company)
    RelativeLayout reCompany;
    @BindView(R.id.re_dropOut)
    RelativeLayout reDropOut;
    private ImageView mCollectView;
    private boolean mIsSelected;
    JpUserInfo userinfo = null;
    private Activity mContext;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ZhubaoInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.zhubao_info);
        ButterKnife.bind(this);
        mContext=this;
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            userinfo = new JpUserInfo();
            userinfo = preferencesUtil.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.setUser(userinfo);

        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_info_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));

        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });

        titleBar.setTitle("我的助宝");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);

//        holder.re_Company = (RelativeLayout) this.findViewById(R.id.re_Company);
//        holder.re_Company.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ZhubaoUserInfoActivity.this, CompanyActivity.class));
//                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//            }
//        });
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            zhubao_Login_boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }


    @OnClick({R.id.re_Company, R.id.re_dropOut,R.id.re_intercalate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_Company:
                startActivity(new Intent(ZhubaoUserInfoActivity.this, CompanyActivity.class));
                break;
            case R.id.re_dropOut:
                zhubao_Login_boolean = false;
                startActivity(new Intent(ZhubaoUserInfoActivity.this, MainActivity.class));
                JpUser user = new JpUser();
                preferencesUtil=new SharedPreferencesUtil(getApplicationContext());

                try {
                    user = preferencesUtil.getZhubaoUsertInfo();
                } catch (Exception e) {
                    Logger.e("Jp登录",e);
                }
                user.setVippsd("");
                preferencesUtil.saveZhubaoUserInfo(user);
                break;
            case R.id.re_intercalate:
                startActivity(new Intent(ZhubaoUserInfoActivity.this, BasicSettingsActivity.class));
                break;
        }
    }
}
