package com.zhubaokeji.android.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhubaokeji.android.BuildConfig;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2016/12/10.
 */

public class CompanyActivity extends BaseActivity {
    @BindView(R.id.versionNumber)
    TextView versionNumber;
    private Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_company);
        ButterKnife.bind(this);
        mContext=this;
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_Company_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("关于我们");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });

        String versionName = BuildConfig.VERSION_NAME;
        versionNumber.setText(versionName);
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            zhubao_Login_boolean=false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }
}
