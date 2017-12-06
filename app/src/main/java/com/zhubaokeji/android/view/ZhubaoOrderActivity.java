//package com.zhubaokeji.android.view;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.lzy.okgo.OkGo;
//import com.zhubaokeji.android.R;
//import com.zhubaokeji.android.adapter.TabAdapter;
//import com.zhubaokeji.android.bean.JpUserInfo;
//import com.zhubaokeji.android.bean.LzyResponse;
//import com.zhubaokeji.android.bean.TabEntity;
//import com.zhubaokeji.android.callback.JsonCallback;
//import com.zhubaokeji.android.fragment.MyStatusView;
//import com.zhubaokeji.android.fragment.StatusLayoutFragment;
//import com.zhubaokeji.android.fragment.ZhubaoOrderFragment;
//import com.zhubaokeji.android.base.BaseActivity;
//import com.zhubaokeji.android.utils.NetUtil;
//import com.zhubaokeji.android.utils.SharedPreferencesUtil;
//import com.zhubaokeji.android.utils.ToastUtil;
//import com.zhubaokeji.android.utils.Urls;
//import com.zhubaokeji.android.utils.ViewFindUtils;
//import com.zhubaokeji.library.CommonTabLayout;
//import com.zhubaokeji.library.MaterialSearchView;
//import com.zhubaokeji.library.SlidingTabLayout;
//import com.zhubaokeji.library.TitleBar;
//import com.zhubaokeji.library.listener.CustomTabEntity;
//import com.zhubaokeji.library.listener.OnTabSelectListener;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;
//
///**
// * Created by fisho on 2017/7/13.
// */
//
//public class ZhubaoOrderActivity extends BaseActivity {
//    @BindView(R.id.search_view)
//    MaterialSearchView searchView;
//    @BindView(R.id.tab_FindFragment_title)
//    SlidingTabLayout tabFindFragmentTitle;
//    @BindView(R.id.zhubao_viewpager)
//    ViewPager zhubaoViewpager;
//    @BindView(R.id.status_layout)
//    StatusLayoutFragment statusLayout;
//
//    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
//    JpUserInfo userInfo;
//    private Activity mContext;
//    private ImageView mCollectView;
//    MyStatusView statusView;
//    public static List<String> mTitle = new ArrayList<String>();
//    private List<String> mDatas = new ArrayList<String>();
//    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//    private MyPagerAdapter adapter;
//    private final String[] mTitles = {
//            "意向订单", "采购中", "采购成功"
//            , "待发货", "未结清", "完成订单", "全部订单"
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zhubao_order);
//        ButterKnife.bind(this);
//        mContext=this;
//        /**
//         * 设置标题栏
//         */
//        boolean isImmersive = false;
//        searchView.setVoiceSearch(false);
//        searchView.setCursorDrawable(R.drawable.custom_cursor);
//        searchView.setEllipsize(true);
////        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (NetUtil.isConnected(mContext) != true) {
//                    ToastUtil.show(mContext, "");
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_order_title);
//        titleBar.setImmersive(isImmersive);
//        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
//        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
//        titleBar.setLeftTextColor(Color.WHITE);
//        titleBar.setTitle("我的订单");
//        titleBar.setTitleColor(Color.WHITE);
//        titleBar.setSubTitleColor(Color.WHITE);
//        titleBar.setDividerColor(Color.GRAY);
//        titleBar.setActionTextColor(Color.WHITE);
//        titleBar.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.in_right, R.anim.out_right);
//            }
//        });
//        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_action_action_search) {
//            @Override
//            public void performAction(View view) {
//                searchView.setView(view);
//            }
//        });
//        //获取本地的用户信息
//        preferencesUtil = new SharedPreferencesUtil(this);
//        try {
//            userInfo = preferencesUtil.getInfo();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        initDatas();
//        // 初始化fragment
//        for (int i = 0; i < mTitles.length; i++) {
//            fragments.add(ZhubaoOrderFragment.newInstance(mDatas.get(i)));
//        }
//
//        View decorView = getWindow().getDecorView();
//        ViewPager vp = ViewFindUtils.find(decorView, R.id.zhubao_viewpager);
//        adapter = new MyPagerAdapter(getSupportFragmentManager());
//        vp.setAdapter(adapter);
//        tabFindFragmentTitle.setViewPager(vp);
//        statusView = new MyStatusView(getApplicationContext());
//        statusLayout.setStatusView(statusView);
//        if (NetUtil.isJpQuery(this) != true) {
//            statusLayout.showSetting();
//        }
//    }
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//    }
//    public void initDatas() {
//        mDatas.add("1");
//        mDatas.add("2");
//        mDatas.add("3");
//        mDatas.add("4");
//        mDatas.add("5");
//        mDatas.add("6");
//        mDatas.add("");
//    }
//}
