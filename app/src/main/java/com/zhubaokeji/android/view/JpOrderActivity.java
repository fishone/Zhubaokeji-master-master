package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.TabAdapter;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpOrderRequest;
import com.zhubaokeji.android.bean.JpOrderResponse;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.TabEntity;
import com.zhubaokeji.android.fragment.JpOrderFragment;
import com.zhubaokeji.android.fragment.MyStatusView;
import com.zhubaokeji.android.fragment.StatusLayoutFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.ViewFindUtils;
import com.zhubaokeji.library.CommonTabLayout;
import com.zhubaokeji.library.MaterialSearchView;
import com.zhubaokeji.library.NoScrollViewPager;
import com.zhubaokeji.library.TitleBar;
import com.zhubaokeji.library.listener.CustomTabEntity;
import com.zhubaokeji.library.listener.OnTabSelectListener;
import com.lzy.okgo.model.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/4/24.
 */

public class JpOrderActivity extends BaseActivity {
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;
    private CommonTabLayout mTabLayout;
    private NoScrollViewPager mViewPager;

    public static List<String> mTitle = new ArrayList<String>();
    private List<String> mDatas = new ArrayList<String>();
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private TabAdapter adapter;
    private ImageView mCollectView;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    JpUserInfo jpUserInfo;
    private MaterialSearchView searchView;
    JpOrderRequest jpOrderRequest;
    private boolean paging = false;
    ArrayList<JpOrderResponse> responseList = new ArrayList<JpOrderResponse>();
    ArrayList<JpOrderResponse> orderResultList = new ArrayList<JpOrderResponse>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    MyStatusView statusView;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_order);
        ButterKnife.bind(this);
        /**
         * 设置标题栏
         */
        boolean isImmersive = false;
        mContext=this;
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(NetUtil.isConnected(mContext) !=true){
                    ToastUtil.show(mContext,"");
                }
                jpOrderRequest = new JpOrderRequest();
                jpOrderRequest.setS_num(query);
                jpOrderRequest.setS_numtype("0");
                jpOrderRequest.setS_state("12");
                jpOrderRequest.setToken(jpUserInfo.getToken());
                Intent result_intent = new Intent();
                result_intent.setClass(JpOrderActivity.this, JpOrderResultActivity.class);
                result_intent.putExtra("jpOrderRequest", jpOrderRequest);
                startActivity(result_intent);
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_order_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("我的订单");
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
        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_action_action_search) {
            @Override
            public void performAction(View view) {
                searchView.setView(view);
            }
        });
        preferencesUtil = new SharedPreferencesUtil(this);
        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTabLayout = (CommonTabLayout) findViewById(R.id.tab_FindFragment_title);
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_FindFragment_pager);
        initDatas();
        for (int i = 0; i < mTitle.size(); i++) {
            fragments.add(JpOrderFragment.newInstance(mDatas.get(i)));
        }
        for (int i = 0; i < mTitle.size(); i++) {
            mTabEntities.add(new TabEntity(mTitle.get(i)));
        }
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp_FindFragment_pager);

        adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        orderCount(vp);
        remind();
        statusView = new MyStatusView(getApplicationContext());
        statusLayout.setStatusView(statusView);
        if (NetUtil.isJpQuery(this) != true) {
            statusLayout.showSetting();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
    private void remind() {
        String path = Urls.URL + Urls.JPOREDERREMIND + "&token=" + jpUserInfo.getToken();
        OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                    @Override
                    public void onSuccess(Response<LzyResponse> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyResponse lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        if(null != message && !message.equals("")){
                                            ToastUtil.show(JpOrderActivity.this, message);
                                            break;
                                        }
                                        break;
                                    case 404:
                                    case -404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpOrderActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        if(null != message && !message.equals("")) {
                                            ToastUtil.show(mContext, message);
                                            break;
                                        }
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.jpException(mContext,response.getException());
                    }
                });
    }

    Random mRandom = new Random();
    void orderCount(final ViewPager viewPager) {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == -1) {
                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
        String path = Urls.URL + Urls.JPORDERCOUNTURL + "&token=" + jpUserInfo.getToken();
        OkGo.<LzyResponse<HashMap<String,String>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<HashMap<String,String>>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<HashMap<String,String>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyResponse<HashMap<String, String>> lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        HashMap<String, String> data = new HashMap<String, String>();
                                        data=lzyResponse.getMsgdata();
                                        if (data.get("待取货-未到") != null && !data.get("待取货-未到").equals("")) {
                                            String count = data.get("待取货-未到");
                                            mTabLayout.showMsg(1, Integer.parseInt(count));
                                            mTabLayout.setMsgMargin(1, -4, 10);
                                        }
                                        if (data.get("待取货-已到") != null && !data.get("待取货-已到").equals("")) {
                                            String count = data.get("待取货-已到");
                                            mTabLayout.showMsg(0, Integer.parseInt(count));
                                            mTabLayout.setMsgMargin(0, -7, 10);
                                        }
                                        break;
                                    case 404:
                                    case -404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpOrderActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(getApplicationContext(), message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse<HashMap<String,String>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.jpException(mContext,response.getException());
                    }
                });
    }

    public void initDatas() {
        mTitle = new ArrayList<>();
        mTitle.add("已到待取");
        mTitle.add("未到待取");
        mTitle.add("已完成");
        mTitle.add("全部订单");
        mDatas.add("4");
        mDatas.add("10");
        mDatas.add("6");
        mDatas.add("12");
    }
}
