package com.zhubaokeji.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.ZhubaoOrderAdapter;
import com.zhubaokeji.android.base.BaseFragment;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoOrderRequest;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.bean.ZhubaoOrderResponse;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.view.ZhubaoLoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by fisho on 2017/7/13.
 */

public class ZhubaoOrderFragment extends BaseFragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    @BindView(R.id.totalCost)
    LinearLayout totalCost;
    @BindView(R.id.pull_icon)
    ImageView pullIcon;
    @BindView(R.id.refreshing_icon)
    ImageView refreshingIcon;
    @BindView(R.id.state_tv)
    TextView stateTv;
    @BindView(R.id.state_iv)
    ImageView stateIv;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.pullup_icon)
    ImageView pullupIcon;
    @BindView(R.id.loading_icon)
    ImageView loadingIcon;
    @BindView(R.id.loadstate_tv)
    TextView loadstateTv;
    @BindView(R.id.loadstate_iv)
    ImageView loadstateIv;
    @BindView(R.id.loadmore_view)
    RelativeLayout loadmoreView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;
    private int type;
    MyStatusView statusView;
    private int tableRow = -1;
    private int pageIndex = 1;
    private static final int FREFRESH = 10001;
    private static final int LOADMORE = 10002;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    JpUserInfo userInfo;
    ZhubaoOrderRequest zhubaoOrderRequest;
    ZhubaoOrderAdapter zhubaoOrderAdapter;
    ArrayList<ZhubaoOrderResponse> responseList = new ArrayList<ZhubaoOrderResponse>();
    ArrayList<ZhubaoOrderResponse> list = new ArrayList<ZhubaoOrderResponse>();

    public static ZhubaoOrderFragment newInstance(String type) {
        ZhubaoOrderFragment fragment = new ZhubaoOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = Integer.parseInt((String) getArguments().getSerializable(TABLAYOUT_FRAGMENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jp_order_fragment, container, false);
        ButterKnife.bind(this, view);
        statusView = new MyStatusView(getContext()).setRetryClickLister(new MyStatusView.onRetryClickLister() {
            @Override
            public void onRetryClick() {

            }
        }).setEmptyText("您还没有相关的订单");
        statusLayout.setStatusView(statusView);

        preferencesUtil = new SharedPreferencesUtil(getContext());
        try {
            userInfo = preferencesUtil.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        zhubaoOrderRequest = new ZhubaoOrderRequest();
        zhubaoOrderRequest.setToken(userInfo.getToken());
        zhubaoOrderAdapter = new ZhubaoOrderAdapter();
        tableRow = -1;
        refreshLayout.releasePull(false, false);
        if (type == 12) {
            pageIndex = 1;
            refreshLayout.releasePull(true, true);
        }
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                responseList.clear();
                initData(FREFRESH);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pageIndex += 1;
                zhubaoOrderRequest.setS_page(String.valueOf(pageIndex));
                initData(LOADMORE);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        return view;
    }

    @Override
    protected void initPrepare() {
    }

    @Override
    protected void onInvisible() {
    }

    @Override
    protected void initData(final int freshen) {
        if (NetUtil.isZhubaoQuery(getContext()) != true) {
            statusLayout.showSetting();
            return;
        }
        zhubaoOrderRequest.setS_state(String.valueOf(type));
        String path = Urls.ZHUBAOURL + Urls.ZHUBAOQUERYORDER + zhubaoOrderRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoOrderResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoOrderResponse>>>(getActivity()) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyListResponse<ArrayList<ZhubaoOrderResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<ZhubaoOrderResponse>> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoOrderResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        list= GsonUtil.FromJson(serverModle.rows,ZhubaoOrderResponse.class);
                                        if(list !=null && list.size()!=0){
//                                            BigDecimal perice = new BigDecimal(0.00);
//                                            for (int i = 0; i < list.size(); i++) {
//                                                BigDecimal endsaleprice = new BigDecimal(list.get(i).getEndsaleprice());
//                                                perice = perice.add(endsaleprice);
//                                            }
//                                            switch (type) {
//                                                case 10:
//                                                case 4:
//                                                    totalCost.setVisibility(View.VISIBLE);
//                                                    endsaleprice.setText(perice.toString());
//                                                    break;
//                                            }
                                            ArrayList<String> titleData = new ArrayList<>();
                                            titleData.add("货号");
                                            titleData.add("订单号");
                                            titleData.add("日期");
                                            titleData.add("类型");
                                            titleData.add("形状");
                                            titleData.add("克拉");
                                            titleData.add("强度");
                                            titleData.add("光彩");
                                            titleData.add("颜色");
                                            titleData.add("净度");
                                            titleData.add("切工");
                                            titleData.add("抛光");
                                            titleData.add("对称");
                                            titleData.add("荧光");
                                            titleData.add("奶色");
                                            titleData.add("咖色");
                                            titleData.add("证书");
                                            titleData.add("证书号");
                                            titleData.add("退点");
                                            titleData.add("RMB/pc");
                                            titleData.add("状态");
                                            zhubaoOrderAdapter.setTitleList(titleData);
                                            if (freshen == FREFRESH) {
                                                responseList.clear();
                                                if(list.size()<25){
                                                    refreshLayout.releasePull(false,false);
                                                }
                                                responseList.addAll(list);
                                            } else {
                                                if(list.size()<25){
                                                    refreshLayout.releasePull(false,false);
                                                }
                                                responseList.addAll(list);
                                            }
                                            zhubaoOrderAdapter.setContentList(responseList, tableRow);
                                            scrollablePanel.setPanelAdapter(zhubaoOrderAdapter);
                                        }else{
                                            statusLayout.showEmpty();
                                        }
                                        break;
                                    case -1:
                                    case 0:
                                        statusLayout.showEmpty();
                                        break;
                                    case 404:
                                    case -404:
                                        zhubao_Login_boolean = false;
                                        ToastUtil.show(getActivity(),"登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(getContext(), ZhubaoLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(getActivity(),message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        zhubaoOrderAdapter.setOnItemClickListener(new ZhubaoOrderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onRowClick(View view, int tag) {
                tableRow=tag;
                zhubaoOrderAdapter.setContentList(responseList,tableRow);
                scrollablePanel.notifyDataSetChanged();
            }
        });
        return;
    }

}
