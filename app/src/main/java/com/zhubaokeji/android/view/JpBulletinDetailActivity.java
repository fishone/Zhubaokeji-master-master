package com.zhubaokeji.android.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpBulletinResponse;
import com.zhubaokeji.android.databinding.JpBulletinDetailBinding;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/5/12.
 */

public class JpBulletinDetailActivity extends BaseActivity{
    private Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JpBulletinDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.jp_bulletin_detail);
        ButterKnife.bind(this);
        mContext=this;
        /*设置标题*/
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_bulletin_detail_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
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

        JpBulletinResponse jpBulletinResponse = getIntent().getParcelableExtra("jpBulletinResponse");     //bulletin 内容
        titleBar.setTitle(jpBulletinResponse.getTitle());
        binding.setDetail(jpBulletinResponse);
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }
}
