package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.adapter.ColorResultAdapter;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.lzy.okgo.model.Response;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by fisho on 2017/4/1.
 */

public class ZhubaoColorResultActivity extends BaseActivity {
    @BindView(R.id.zhubao_color_result_parameter)
    TextView zhubaoColorResultParameter;
    ColorResultAdapter colorResultAdapter;
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;

    private static final int FREFRESH = 10001;
    private static final int LOADMORE = 10002;
    ArrayList<ZhubaoColorResponse> list=null;
    ArrayList<ZhubaoColorResponse> resultList=null;
    JpSearchRequest diamondRequest;
    private int tableRow=-1;
    private int pageIndex=1;
    private Activity mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_color_result);
        ButterKnife.bind(this);
        mContext=this;

        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_color_result_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.setTitle("助宝彩钻结果");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

        ArrayList<String> titleData = new ArrayList<>();
        titleData.add("供应商");
        titleData.add("USD/CT");
        titleData.add("USD/粒");
        titleData.add("形状");
        titleData.add("克拉");
        titleData.add("颜色");
        titleData.add("强度");
        titleData.add("光彩");
        titleData.add("均衡");
        titleData.add("净度");
        titleData.add("抛光");
        titleData.add("对称");
        titleData.add("荧光");
        titleData.add("证书");
         list = getIntent().getParcelableArrayListExtra("list");
         diamondRequest = getIntent().getParcelableExtra("request");
        if(list.size()<25){
            refreshLayout.releasePull(false,false);
        }

        zhubaoColorResultParameter.setText(diamondRequest.toString());
        colorResultAdapter = new ColorResultAdapter();
        colorResultAdapter.setTitleList(titleData);
        colorResultAdapter.setContentList(list, tableRow, diamondRequest);
        scrollablePanel.setPanelAdapter(colorResultAdapter);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                list.clear();
                PullingUp(FREFRESH);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pageIndex+=1;
                diamondRequest.setS_page(pageIndex);
                diamondRequest.setS_size(25);
                PullingUp(LOADMORE);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        colorResultAdapter.setOnItemClickListener(new ColorResultAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onRowClick(View view, int tag, ZhubaoColorResponse zhubaoColorResponse) {
                tableRow=tag;
                colorResultAdapter.setContentList(list,tableRow,diamondRequest);
                Intent Intent_Result=new Intent();
                Intent_Result.setClass(ZhubaoColorResultActivity.this, ZhubaoColorDetailsActivity.class);
                Intent_Result.putExtra("detailsDiamond", zhubaoColorResponse);
                Intent_Result.putExtra("diamondRequest", diamondRequest);
                scrollablePanel.notifyDataSetChanged();
                startActivity(Intent_Result);
            }

            @Override
            public void onSortClick(View view, int tag) {
                switch (tag){
                    case 2:case 3:case 5:
                        if(tag ==2){
                            diamondRequest.setS_qorder("saledollorctprice");
                        }else {
                            diamondRequest.setS_qorder("carat");
                        }
                        if(diamondRequest.getS_qorderdir().equals("asc")){
                            diamondRequest.setS_qorderdir("desc");
                        }else {
                            diamondRequest.setS_qorderdir("asc");
                        }
                        list.clear();
                        PullingUp(FREFRESH);
                        break;
                    default:
                        break;
                }


            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            zhubao_Login_boolean=false;
            startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    private void PullingUp(final int freshen) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(diamondRequest);
        final String path = Urls.ZHUBAOURL+ Urls.ZHUBAOSEARCHURL +diamondRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoColorResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                                                  // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoColorResponse>>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoColorResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<ZhubaoColorResponse>> lzyListResponse =response.body();
                                String message = lzyListResponse.message;
                                switch (lzyListResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoColorResponse>>.ServerModle serverModle= lzyListResponse.getMsgdata();
                                        resultList=GsonUtil.FromJson(serverModle.rows,ZhubaoColorResponse.class);
                                        if (resultList != null) {
                                            if (freshen == FREFRESH) {
                                                if(resultList.size()<25){
                                                    refreshLayout.releasePull(false,false);
                                                }
                                                list.addAll(resultList);
                                            } else {
                                                if(resultList.size()<25){
                                                    refreshLayout.releasePull(false,false);
                                                }
                                                list.addAll(resultList);
                                            }
                                            scrollablePanel.notifyDataSetChanged();
                                        }
                                        break;
                                    case -1:
                                        zhubao_Login_boolean = false;
                                        ToastUtil.show(getApplicationContext(),"登录超时,请重新登录");
                                        startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
                                        break;
                                    default:
                                        Toast toast = Toast.makeText(mContext, "查询失败,请重试", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "message");
                        }
                    }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<ZhubaoColorResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.ZHUBAOKEJI);
                    }
                });
    }
}
