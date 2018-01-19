package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpOrderRequest;
import com.zhubaokeji.android.bean.JpOrderResponse;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.adapter.JpOrderAdapter;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;
import com.lzy.okgo.model.Response;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/4/28.
 */

public class JpOrderResultActivity extends BaseActivity {
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    private boolean paging = false;
    ArrayList<JpOrderResponse> responseList;
    private JpOrderRequest jpOrderRequest;
    private JpOrderAdapter jpOrderAdapter;
    private int tableRow=-1;
    private Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_order_result);
        ButterKnife.bind(this);
        mContext=this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_order_result_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("订单");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jpOrderRequest = getIntent().getParcelableExtra("jpOrderRequest");
        jpOrderAdapter = new JpOrderAdapter();
        generateTestData();
        refreshLayout.releasePull(false,false);
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

    private void generateTestData() {
        ArrayList<String> titleData = new ArrayList<>();
        titleData.add("订单状态");
        titleData.add("货号");
        titleData.add("证书");
        titleData.add("证书号");
        titleData.add("USD/粒");
        titleData.add("克拉");
        titleData.add("颜色");
        titleData.add("净度");
        titleData.add("肉眼干净");
        titleData.add("下单日期");
        titleData.add("预计到货日期");
        jpOrderAdapter.setTitleList(titleData);
        String path = Urls.URL + Urls.JPORDERURL + jpOrderRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<JpOrderResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<JpOrderResponse>>>(this) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<JpOrderResponse>>> response) {
                        try {
                            if (null != response && null!=response.body()) {
                                LzyListResponse<ArrayList<JpOrderResponse>> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<JpOrderResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        responseList=GsonUtil.FromJson(serverModle.rows,JpOrderResponse.class);
                                        if(responseList !=null && responseList.size() !=0){
                                            jpOrderAdapter.setContentList(responseList,tableRow);
                                            scrollablePanel.setPanelAdapter(jpOrderAdapter);
                                        }
                                        break;
                                    case -1:
                                        ToastUtil.show(mContext,message);
                                        break;
                                    case 404:case -404:
                                        jp_Login_Boolean=false;
                                        ToastUtil.show(mContext,"登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpOrderResultActivity.this, JpLoginActivity.class);
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
                    public void onError(Response<LzyListResponse<ArrayList<JpOrderResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });

        jpOrderAdapter.setOnItemClickListener(new JpOrderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onRowClick(View view, int tag) {
                tableRow=tag;
                jpOrderAdapter.setContentList(responseList,tableRow);
                scrollablePanel.notifyDataSetChanged();
            }

//            @Override
//            public void onReportClick(JpOrderResponse jpOrderResponse) {
//                if (jpOrderResponse !=null) {
//                    Intent result_intent = new Intent();
//                    result_intent.setClass(getApplicationContext(), ZhengshuActivity.class);
//                    result_intent.putExtra("reportNo", jpOrderResponse.getReportno());
//                    result_intent.putExtra("reportType", jpOrderResponse.getReport());
//                    startActivity(result_intent);
//                }
//            }
        });
    }
}
