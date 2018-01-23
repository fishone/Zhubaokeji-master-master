package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.ZhuaboResultAdapter;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.lzy.okgo.model.Response;

import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2016/12/22.
 */

public class ZhubaoResultActivity extends BaseActivity {
    List<ZhubaoDiamondResponse> responses;
    ArrayList<ZhubaoDiamondResponse> list;
    ArrayList<ZhubaoDiamondResponse> resultList;
    ZhuaboResultAdapter zhuaboResultAdapter;
    JpSearchRequest diamondRequest;
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.result_parameter)
    TextView resultParameter;
    private int tableRow = -1;
    LoadingDialog dialog; //新建一个ProgressDialog
    private static final int FREFRESH = 10001;
    private static final int LOADMORE = 10002;
    private static final int ZHUBAOCART = 1003;
    private int pageIndex = 1;
    private Activity mContext;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_result);
        ButterKnife.bind(this);
        mContext = this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_result_title);
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

        titleBar.setTitle("助宝白钻结果");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        ArrayList<String> titleData = new ArrayList<>();
        titleData.add("供应商");
        titleData.add("退点");
        titleData.add("USD/粒");
        titleData.add("奶色");
        titleData.add("咖色");
        titleData.add("绿色");
        titleData.add("形状");
        titleData.add("克拉");
        titleData.add("颜色");
        titleData.add("净度");
        titleData.add("切工");
        titleData.add("抛光");
        titleData.add("对称");
        titleData.add("荧光");
        titleData.add("证书");
        list = getIntent().getParcelableArrayListExtra("list");
        diamondRequest = getIntent().getParcelableExtra("request");
        if (list.size() < 25) {
            refreshLayout.releasePull(false, false);
        }
        resultParameter.setText(diamondRequest.toString());
        zhuaboResultAdapter = new ZhuaboResultAdapter();
        zhuaboResultAdapter.setTitleList(titleData);

        zhuaboResultAdapter.setContentList(list, tableRow, diamondRequest);
        scrollablePanel.setPanelAdapter(zhuaboResultAdapter);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                list.clear();
                PullingUp(FREFRESH);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pageIndex += 1;
                diamondRequest.setS_page(pageIndex);
                diamondRequest.setS_size(25);
                PullingUp(LOADMORE);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        zhuaboResultAdapter.setOnItemClickListener(new ZhuaboResultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final int tag, ZhubaoDiamondResponse zhubaoDiamondResponse) {
                if (zhubaoDiamondResponse.isPlaceOrder()) {
//                    fabShoppingcart.increase(); // Increase the current count value by 1
                    final String path = Urls.ZHUBAOURL + Urls.ZHUBAOADDCART + "&s_diano=" + zhubaoDiamondResponse.getDiano() + "&token=" + diamondRequest.getToken() +"&s_protype=0";
                    OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                            .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                            .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                                @Override
                                public void onSuccess(Response<LzyResponse> response) {
                                    try {
                                        if (null != response && null !=response) {
                                            LzyResponse lzyResponse=response.body();
                                            String message = lzyResponse.message;
                                            switch (lzyResponse.status) {
                                                case 1:
                                                    list.get(tag).setIshold("1");
                                                    ToastUtil.show(getApplicationContext(),message);
                                                    scrollablePanel.notifyDataSetChanged();
                                                    break;
                                                case 404:
                                                case -404:
                                                    zhubao_Login_boolean=false;
                                                    ToastUtil.show(getApplicationContext(),"登录超时,请重新登录");
                                                    Intent login_intent = new Intent();
                                                    login_intent.setClass(ZhubaoResultActivity.this, ZhubaoLoginActivity.class);
                                                    startActivity(login_intent);
                                                    break;
                                                default:
                                                    ToastUtil.show(getApplicationContext(),message);
                                                    break;
                                            }
                                        }
                                    } catch (Exception e) {
                                        Logger.e(e, "助宝白钻添加购物车");
                                    }
                                }
                                @Override
                                public void onError(Response<LzyResponse> response) {
                                    dialog.close();
                                    //网络请求失败的回调,一般会弹个Toast
                                    NetUtil.myException(mContext,response.getException(), FlagUtil.ZHUBAOKEJI);
                                }
                            });
                }
            }

            @Override
            public void onRowClick(View view, int tag, ZhubaoDiamondResponse detailsDiamond) {
                tableRow = tag;
                zhuaboResultAdapter.setContentList(list, tableRow, diamondRequest);
                Intent Intent_Result = new Intent();
                Intent_Result.setClass(ZhubaoResultActivity.this, ZhubaoDetailsActivity.class);
                Intent_Result.putExtra("detailsDiamond", detailsDiamond);
                Intent_Result.putExtra("diamondRequest", diamondRequest);
                scrollablePanel.notifyDataSetChanged();
                startActivity(Intent_Result);
            }

            @Override
            public void onSortClick(View view, int tag) {
                switch (tag) {
                    case 2:case 3:case 8:
                        if (tag == 2) {
                            diamondRequest.setS_qorder("salediscount");
                        } else if (tag == 3) {
                            diamondRequest.setS_qorder("rmbprice");
                        } else {
                            diamondRequest.setS_qorder("carat");
                        }
                        if (diamondRequest.getS_qorderdir().equals("asc")) {
                            diamondRequest.setS_qorderdir("desc");
                        } else {
                            diamondRequest.setS_qorderdir("asc");
                        }
                        list.clear();
                        PullingUp(FREFRESH);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onImageClick(View view, int tag, ZhubaoDiamondResponse zhubaoDiamondResponse) {
                if(!zhubaoDiamondResponse.getImgurl().equals("")){
                    Intent result_intent = new Intent();
                    result_intent.setClass(ZhubaoResultActivity.this, PictureActity.class);
                    result_intent.putExtra("url", zhubaoDiamondResponse.getImgurl());
                    startActivity(result_intent);
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
        if(NetUtil.isZhubaoQuery(mContext) ==false){
            startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
            return;
        }
        final String path = Urls.ZHUBAOURL + Urls.ZHUBAOSEARCHURL + diamondRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<ZhubaoDiamondResponse>> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoDiamondResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        resultList=GsonUtil.FromJson(serverModle.rows,ZhubaoDiamondResponse.class);
                                        if (resultList != null) {
                                            if (freshen == FREFRESH) {
                                                if (resultList.size() < 25) {
                                                    refreshLayout.releasePull(false, false);
                                                }
                                                list.addAll(resultList);
                                            } else {
                                                if (resultList.size() < 25) {
                                                    refreshLayout.releasePull(false, false);
                                                }
                                                list.addAll(resultList);
                                            }
                                            scrollablePanel.notifyDataSetChanged();
                                        }
                                        break;
                                    case -1:
                                        zhubao_Login_boolean = false;
                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
                                        startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
                                        break;
                                    default:
                                        ToastUtil.show(mContext,message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "message");
                        }
                    }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                        dialog.close();
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(),FlagUtil.ZHUBAOKEJI);
                    }
                });
    }
}
