package com.zhubaokeji.android.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.GoladPriceAdapter;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.ResultBean;
import com.zhubaokeji.android.bean.goldResponse;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.fragment.MyStatusView;
import com.zhubaokeji.android.fragment.StatusLayoutFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2017/1/3.
 */

public class GoldPriceActivity extends BaseActivity {
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    ArrayList<goldResponse> goldRequestList = new ArrayList<goldResponse>();
    @BindView(R.id.recycler_gold)
    PullableScrollablePanel recyclerGold;
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;
    private Handler handler;
    GoladPriceAdapter adapter;
    MyStatusView statusView;
    private Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_price);
        ButterKnife.bind(this);
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.gold_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("金价查询");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                timer.cancel();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });
        mContext=this;
        statusView =new MyStatusView(this);
        statusLayout.setStatusView(statusView);
        if(NetUtil.isConnected(this) != true) {
            statusLayout.showSetting();
            return;
        }else {
            timer.schedule(task, 10, 10000); // 1s后执行task,经过10s再次执行
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            zhubao_Login_boolean=false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
    Timer timer = new Timer();
    final String path = Urls.GOLDPRICEURL;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                OkGo.<String>get(path)     // 请求方式和请求url
                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                try {
                                    if (null != response && null !=response.body()) {
                                        String  jsonStr=response.body();
                                        JSONObject jsonObject = new JSONObject(jsonStr);
                                        int status = jsonObject.getInt("status");
                                        switch (status) {
                                            case 380:
                                                ResultBean resultBean = new Gson().fromJson(jsonStr, ResultBean.class);
                                                goldRequestList = GsonUtil.FromJson(resultBean.getResult(), goldResponse.class); //取出金价信息
                                                if (goldRequestList != null && goldRequestList.size() != 0) {
                                                    int width = recyclerGold.getWidth() / 4;
                                                    adapter = new GoladPriceAdapter();
                                                    adapter.setTitleList(goldRequestList,width);
                                                    recyclerGold.setPanelAdapter(adapter);
                                                }
                                                break;
                                            default:
                                                ToastUtil.show(GoldPriceActivity.this, "没有查询到金价,请重试");
                                                break;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
