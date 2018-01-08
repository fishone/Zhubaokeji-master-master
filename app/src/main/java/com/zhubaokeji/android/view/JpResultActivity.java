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
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.adapter.JpResultAdapter;
import com.zhubaokeji.android.biz.PullToRefreshLayout;
import com.zhubaokeji.android.biz.PullableScrollablePanel;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.CounterFab;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.lzy.okgo.model.Response;

import static com.zhubaokeji.android.fragment.JpHomeFragment.R0llTEXT;
import static com.zhubaokeji.android.fragment.JpHomeFragment.jpShoppingCartResponses;
import static com.zhubaokeji.android.view.JpShoppingCartActivity.responseList;
import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
/**
 * Created by fisho on 2017/4/21.
 */

public class JpResultActivity extends BaseActivity {
    ArrayList<ZhubaoDiamondResponse> list = new ArrayList<>();
    JpSearchRequest diamondRequest;
    protected static final int SUCCESS = 1;
    protected static final int ERROR = 0;
    LoadingDialog dialog; //新建一个ProgressDialog
    ArrayList<ZhubaoDiamondResponse> resultList = new ArrayList<ZhubaoDiamondResponse>();
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    private static final int FREFRESH = 10001;
    private static final int LOADMORE = 10002;
    @BindView(R.id.scrollable_panel)
    PullableScrollablePanel scrollablePanel;
    @BindView(R.id.fab_shoppingcart)
    CounterFab fabShoppingcart;
    @BindView(R.id.jp_textRoll_Result)
    TextView jpTextRollResult;
    private JpResultAdapter jpResultAdapter;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    private JpUserInfo jpUserInfo;
    private static final int RESULTCODE = 1;
    private int tableRow=-1;
    private int pageIndex;
    private Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_result);
        Logger.d("执行了 onCreate");
        mContext=this;
        ButterKnife.bind(this);

        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_result_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("JP白钻结果");
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

        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = getIntent().getParcelableArrayListExtra("list");
        diamondRequest = getIntent().getParcelableExtra("request");
        if(list.size()<25){
            refreshLayout.releasePull(false,false);
        }
        TextView result_parameter = (TextView) findViewById(R.id.result_parameter);
        result_parameter.setText(diamondRequest.toString());
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
                diamondRequest.setPage(pageIndex);
                PullingUp(LOADMORE);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        dialog=new LoadingDialog(this,"玩命加载中...");
        jpTextRollResult.setText(R0llTEXT);
        jpResultAdapter = new JpResultAdapter();
        generateTestData();
        scrollablePanel.setPanelAdapter(jpResultAdapter);
        if(jpShoppingCartResponses !=null){
            fabShoppingcart.setCount(jpShoppingCartResponses.size()); // 角标赋值
        }
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

    private void PullingUp(final int freshen) {
        final int refreshResult;
        final String path = Urls.URL + Urls.JPSEARCHURL + diamondRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                                                     // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse <ArrayList<ZhubaoDiamondResponse>>>(this) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyListResponse <ArrayList<ZhubaoDiamondResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<ZhubaoDiamondResponse>> lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoDiamondResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        resultList=GsonUtil.FromJson(serverModle.rows,ZhubaoDiamondResponse.class);
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
                                    case -404: case 404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(mContext,"登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpResultActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
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
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });
    }

    private void generateTestData() {
        jpResultAdapter.setLeftInfoList(list);
        List<String> dateInfoList = new ArrayList<>();
        dateInfoList.add("证书号");
        dateInfoList.add("原退点");
        dateInfoList.add("退点");
        dateInfoList.add("形状");
        dateInfoList.add("克拉");
        dateInfoList.add("颜色");
        dateInfoList.add("净度");
        dateInfoList.add("切工");
        dateInfoList.add("抛光");
        dateInfoList.add("对称");
        dateInfoList.add("荧光");
        dateInfoList.add("肉眼干净");
        dateInfoList.add("奶咖绿");
        dateInfoList.add("地址");
        dateInfoList.add("证书");

        jpResultAdapter.setTitleList(dateInfoList);
        jpResultAdapter.setContentList(list,tableRow,diamondRequest);
        jpResultAdapter.setJpUserInfo(jpUserInfo);
        jpResultAdapter.setOnItemClickListener(new JpResultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final int tag, ZhubaoDiamondResponse zhubaoDiamondResponse) {
                if (zhubaoDiamondResponse.isPlaceOrder()) {
                    fabShoppingcart.increase(); // Increase the current count value by 1
                    final String path = Urls.URL + Urls.JPADDSHOPPINGCART + "&needno=" + zhubaoDiamondResponse.getDiano() + "&token=" + diamondRequest.getToken();
                    OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                            .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                            .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                                @Override
                                public void onSuccess(Response<LzyResponse> response) {
                                    try {
                                        if (null != response && null !=response.body()) {
                                            LzyResponse lzyResponse=response.body();
                                            String message = lzyResponse.message;
                                            switch (lzyResponse.status) {
                                                case 1:
                                                    list.get(tag).setIshold("1");
                                                    ToastUtil.show(getApplicationContext(),message);
                                                    scrollablePanel.notifyDataSetChanged();
                                                    break;
                                                case -1:
                                                    ToastUtil.show(getApplicationContext(),message);
                                                case 404:
                                                case -404:
                                                    jp_Login_Boolean=false;
                                                    ToastUtil.show(getApplicationContext(),"登录超时,请重新登录");
                                                    Intent login_intent = new Intent();
                                                    login_intent.setClass(JpResultActivity.this, JpLoginActivity.class);
                                                    startActivity(login_intent);
                                                    break;
                                                default:
                                                    ToastUtil.show(getApplicationContext(),message);
                                                    break;
                                            }
                                        }
                                    } catch (Exception e) {
                                        Logger.e(e, "Jp添加购物车");
                                    }
                                }
                                @Override
                                public void onError(Response<LzyResponse> response) {
                                    //网络请求失败的回调,一般会弹个Toast
                                    NetUtil.myException(mContext,response.getException(),FlagUtil.JP);
                                }
                            });
                }
            }

            @Override
            public void onRowClick(View view, int tag, ZhubaoDiamondResponse zhubaoDiamondResponse) {
                tableRow=tag;
                jpResultAdapter.setContentList(list,tableRow,diamondRequest);
                scrollablePanel.notifyDataSetChanged();
                Intent Intent_Result=new Intent();
                Intent_Result.setClass(JpResultActivity.this, JpDetailsActivity.class);
                Intent_Result.putExtra("detailsDiamond", zhubaoDiamondResponse);
                Intent_Result.putExtra("diamondRequest",diamondRequest);
                startActivityForResult(Intent_Result, 2);
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
//                JpResultActivity.this.startActivity(Intent_Result);
            }

            @Override
            public void onSortClick(View view, int tag) {
                switch (tag){
                    case 3:case 5:
                        if(tag ==3){
                            diamondRequest.setS_qorder("saleback");
                            if(diamondRequest.getS_qorderdir().equals("asc")){
                                diamondRequest.setS_qorderdir("desc");
                            }else {
                                diamondRequest.setS_qorderdir("asc");
                            }
                        }else if (tag ==5){
                            diamondRequest.setS_qorder("carat");
                            if(diamondRequest.getS_qorderdir().equals("asc")){
                                diamondRequest.setS_qorderdir("desc");
                            }else {
                                diamondRequest.setS_qorderdir("asc");
                            }
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

    @OnClick(R.id.fab_shoppingcart)
    public void onClick() {
        Intent result_intent = new Intent();
        result_intent.setClass(JpResultActivity.this, JpShoppingCartActivity.class);
        startActivityForResult(result_intent, RESULTCODE);
        overridePendingTransition(R.anim.in_left, R.anim.out_lef);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == responseList.size()) {
            if (requestCode == RESULTCODE) {
                fabShoppingcart.setCount(resultCode);
            }
        }else if (resultCode ==2){
            int three = data.getIntExtra("three", 0);
            if(three ==1){
                fabShoppingcart.increase(); // Increase the current count value by 1
                list.get(tableRow).setIshold("1");
                scrollablePanel.notifyDataSetChanged();
            }
        }
    }
}
