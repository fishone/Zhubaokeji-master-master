package com.zhubaokeji.android.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.fragment.JpHomeFragment;
import com.zhubaokeji.android.fragment.ZhubaoHomeFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.StringUtil;
import com.zhubaokeji.android.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private List<Fragment> fragments;
    public static boolean login_boolean = false;
    private ProgressDialog mProgressDialog;
    BasicSetting basicSetting =new BasicSetting();
    private int bottomNavigationBar=0;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext=this;
        try {
            if(getIntent().getStringExtra("BottomNavigationBar") !=null){
                bottomNavigationBar= Integer.parseInt(getIntent().getStringExtra("BottomNavigationBar")); //接收点击详情的数据
            }
        }catch (Exception e){
            Logger.e(e,"home");
        }
        fragments = new ArrayList<>();
        initView();
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            basicSetting = preferencesUtil.getBasicSetting();
            if(!StringUtil.isValidString(basicSetting.getRate())){
                basicSetting.setRate("6.80");
                basicSetting.setDiscountPoint("3.00");
                basicSetting.setPercentage("1.20");
                preferencesUtil.saveBasicSetting(basicSetting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Button btnCheckUpdate = (Button) this.findViewById(R.id.gengxin);
//        btnCheckUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkNewVersion();
//            }
//        });
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            zhubao_Login_boolean = false;
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    private void initView() {
        //得到BottomNavigationBar控件
        BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //设置BottomNavigationBar的模式
        navigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置BottomNavigationBar的背景风格
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setAutoHideEnabled(true);//自动隐藏
        //导航显示数字
//        BadgeItem numberBadgeItem = new BadgeItem()
//                .setBorderWidth(1)
//                .setBackgroundColor(Color.RED)
//                .setText("4")
//                .setHideOnSelect(true);
        //增加了2个Fragment
        //BottomNavigationItem("底部导航ico","底部导航名字")
//        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "助宝").setActiveColorResource(R.color.colorAccent).setBadgeItem(numberBadgeItem))
        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "助宝").setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "JP").setActiveColorResource(R.color.colorAccent))
                .setFirstSelectedPosition(bottomNavigationBar)//默认选择索引为0的菜单
                .initialise();//对导航进行重绘

        fragments = getFragments();
        setDefaultFragment();
        navigationBar.setTabSelectedListener(this);
    }

    /*
        设置默认首页
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_content, fragments.get(bottomNavigationBar));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ZhubaoHomeFragment());
        fragments.add(new JpHomeFragment());
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.replace(R.id.id_content, fragment);
                ft.commitAllowingStateLoss();//选择性的提交，和commit有一定的区别，他不保证数据完整传输
            }
        }
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}

