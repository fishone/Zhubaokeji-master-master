package com.zhubaokeji.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.base.BaseFragment;
import com.zhubaokeji.android.bean.JpOrderRequest;
import com.zhubaokeji.android.bean.JpOrderResponse;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.adapter.JpOrderAdapter;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.view.JpLoginActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/4/26.
 */

public class JpOrderFragment extends BaseFragment {
    @BindView(R.id.endsaleprice)
    TextView endsaleprice;
    @BindView(R.id.totalCost)
    LinearLayout totalCost;
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    ArrayList<JpOrderResponse> responseList = new ArrayList<JpOrderResponse>();
    ArrayList<JpOrderResponse> list = new ArrayList<JpOrderResponse>();
    JpUserInfo jpUserInfo;
    JpOrderRequest jpOrderRequest;
    private boolean paging = false;
    private JpOrderAdapter jpOrderAdapter;
    StatusLayoutFragment statusLayout;
    MyStatusView statusView;
    private int tableRow = -1;
    private int pageIndex = 1;
    private static final int FREFRESH = 10001;
    private static final int LOADMORE = 10002;

    public static JpOrderFragment newInstance(String type) {
        JpOrderFragment fragment = new JpOrderFragment();
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
        statusLayout = (StatusLayoutFragment) view.findViewById(R.id.status_layout);
        statusView = new MyStatusView(getContext()).setRetryClickLister(new MyStatusView.onRetryClickLister() {
            @Override
            public void onRetryClick() {

            }
        }).setEmptyText("您还没有相关的订单");
        statusLayout.setStatusView(statusView);

        ButterKnife.bind(this, view);
        preferencesUtil = new SharedPreferencesUtil(getContext());
        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jpOrderRequest = new JpOrderRequest();
        jpOrderRequest.setToken(jpUserInfo.getToken());
        jpOrderAdapter = new JpOrderAdapter();
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
                jpOrderRequest.setS_pageindex(String.valueOf(pageIndex));
                initData(LOADMORE);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        statusLayout.showLoading();
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
        if (NetUtil.isJpQuery(getContext()) != true) {
            statusLayout.showSetting();
            return;
        }
        jpOrderRequest.setS_state(String.valueOf(type));
        String path = Urls.URL + Urls.JPORDERURL + jpOrderRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<JpOrderResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyListResponse<ArrayList<JpOrderResponse>>>(null) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyListResponse<ArrayList<JpOrderResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<JpOrderResponse>> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        statusLayout.showContent();
                                        LzyListResponse<ArrayList<JpOrderResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        list=GsonUtil.FromJson(serverModle.rows,JpOrderResponse.class);
                                        if(list !=null && list.size()!=0){
                                            BigDecimal perice = new BigDecimal(0.00);
                                            for (int i = 0; i < list.size(); i++) {
                                                BigDecimal endsaleprice = new BigDecimal(list.get(i).getEndsaleprice());
                                                perice = perice.add(endsaleprice);
                                            }
                                            switch (type) {
                                                case 10:
                                                case 4:
                                                    totalCost.setVisibility(View.VISIBLE);
                                                    endsaleprice.setText(perice.toString());
                                                    break;
                                            }
                                            ArrayList<String> titleData = new ArrayList<>();
                                            titleData.add("订单状态");
                                            titleData.add("证书");
                                            titleData.add("证书号");
                                            titleData.add("地点");
                                            titleData.add("USD/粒");
                                            titleData.add("形状");
                                            titleData.add("克拉");
                                            titleData.add("颜色");
                                            titleData.add("净度");
                                            titleData.add("肉眼干净");
                                            titleData.add("下单日期");
                                            jpOrderAdapter.setTitleList(titleData);
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
                                            jpOrderAdapter.setContentList(responseList, tableRow);
                                            scrollablePanel.setPanelAdapter(jpOrderAdapter);
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
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(getContext(),"登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(getContext(), JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(getContext(),message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
//                    result_intent.setClass(getActivity(), ZhengshuActivity.class);
//                    result_intent.putExtra("reportNo", jpOrderResponse.getReportno());
//                    result_intent.putExtra("reportType", jpOrderResponse.getReport());
//                    startActivity(result_intent);
//                }
//            }
        });
        return;
    }
}