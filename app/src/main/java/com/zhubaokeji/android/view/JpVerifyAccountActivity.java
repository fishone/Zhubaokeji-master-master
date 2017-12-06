package com.zhubaokeji.android.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.XEditText;
import com.zhubaokeji.library.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fisho on 2017/6/22.
 */

public class JpVerifyAccountActivity extends BaseActivity {
    @BindView(R.id.verify)
    TextView verify;
    @BindView(R.id.userName)
    XEditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_verifyaccount);
        ButterKnife.bind(this);

        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_Verifyaccount_title);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.verify)
    public void onViewClicked() {
        String name = userName.getText().toString();
        if (name.length() == 0) {
            ToastUtil.show(this, "请输入账号");
            return;
        }
        if(NetUtil.isConnected(this) != true) {
            ToastUtil.show(this,"网络未连接,请检查网络后重试");
            return;
        }
        final String path = Urls.URL + Urls.JPGETVIPINFO+"&&vipaccount="+ name;
        OkGo.<LzyResponse<JpUserInfo>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<JpUserInfo>>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<JpUserInfo>> response) {
                        try {
                            if (null != response && null != response.body()) {
                               LzyResponse<JpUserInfo> lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        JpUserInfo jpUserInfo=lzyResponse.getMsgdata();
                                        if(jpUserInfo !=null){
                                            Intent result_intent = new Intent();
                                            result_intent.setClass(JpVerifyAccountActivity.this, JpModifyPasswordActivity.class);
                                            overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                                            result_intent.putExtra("jpUserInfo", jpUserInfo);
                                            startActivity(result_intent);
                                        }
                                        break;
                                    default:
                                        ToastUtil.show(getApplicationContext(),message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"查询出错，请重试");
                        }
                    }
                });
    }
}
