package com.zhubaokeji.android.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.OnlinePriceAdapter;
import com.zhubaokeji.android.bean.CalculatorRequest;
import com.zhubaokeji.android.bean.gold;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.ResultBean;
import com.zhubaokeji.android.bean.TypeBean;
import com.zhubaokeji.android.biz.PopupWheel;
import com.zhubaokeji.android.fragment.MyStatusView;
import com.zhubaokeji.android.fragment.StatusLayoutFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.library.TitleBar;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.lzy.okgo.model.Response;

/**
 * Created by fisho on 2017/2/27.
 */

public class OnlinepriceTableActivity extends BaseActivity {
    @BindView(R.id.recycler_onlineprice)
    RecyclerView recyclerOnlineprice;
    @BindView(R.id.recycler_onlineprice_color)
    RecyclerView recyclerOnlinepriceColor;
    @BindView(R.id.recycler_onlineprice_clarity)
    RecyclerView recyclerOnlinepriceClarity;
    @BindView(R.id.onlineprice_shape)
    TextView onlinepriceShape;
    @BindView(R.id.onlineprice_carat)
    TextView onlinepriceCarat;
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;
    private ArrayList<String> onlinePriceList;
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    private Handler handler;
    LoadingDialog dialog; //新建一个ProgressDialog
    private static int height;
    private static int selectIndex = 0;
    private static ArrayList<String> shapeList;
    private static ArrayList<String> caratList;
    private static ArrayList<String> colorList;
    private static ArrayList<String> clarityList;
    private static ArrayList<String> popColorList;
    CalculatorRequest calculator = new CalculatorRequest();
    private ArrayList<TypeBean> mList = new ArrayList<TypeBean>();
    private Activity mContext;
    MyStatusView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.onlinepricetable);
        ButterKnife.bind(this);
        mContext = this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = false;
        final TitleBar titleBar = (TitleBar) findViewById(R.id.onlineprice_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });

        titleBar.setTitle("国际报价表");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.addAction(new TitleBar.TextAction("筛选") {
            @Override
            public void performAction(View view) {
                PopupWheel.alertBottomWheelOption(OnlinepriceTableActivity.this, shapeList, caratList, popColorList, clarityList, new PopupWheel.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int shapeItem, int caratItem, int colorItem, int clarityItem) {
                        if (shapeList.get(shapeItem).toString().equals("圆形")) {
                            calculator.setS_shape("round");
                        } else {
                            calculator.setS_shape("irregular");
                        }
                        calculator.setS_carat(caratList.get(caratItem));
                        selectIndex = colorItem * 11 + clarityItem;
                        //给onlineprice 赋值显示条件
                        onlinepriceShape.setText(shapeList.get(shapeItem).toString());
                        onlinepriceCarat.setText("(" + caratList.get(caratItem).toString() + ")");
                        international(calculator);
                    }
                });
            }
        });

        clarityData();
        colorData();
        caratData();
        calculator.setS_shape("round");
        calculator.setS_carat("0.01-0.03");
        onlinepriceCarat.setText(calculator.getS_carat());
        international(calculator);
//        handler = new MyHandler(this);

        // 单项选择
        shapeList = new ArrayList<String>();
        shapeList.add("圆形");
        shapeList.add("异形");


        statusView = new MyStatusView(getApplicationContext());
        statusLayout.setStatusView(statusView);
        if(NetUtil.isConnected(this) != true) {
            statusLayout.showSetting();
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    private void caratData() {
        caratList = new ArrayList<String>();
        caratList.add("0.01-0.03");
        caratList.add("0.04-0.07");
        caratList.add("0.08-0.14");
        caratList.add("0.15-0.17");
        caratList.add("0.18-0.22");
        caratList.add("0.23-0.29");
        caratList.add("0.30-0.39");
        caratList.add("0.40-0.49");
        caratList.add("0.50-0.69");
        caratList.add("0.70-0.89");
        caratList.add("0.90-0.99");
        caratList.add("1.00-1.49");
        caratList.add("1.50-1.99");
        caratList.add("2.00-2.99");
        caratList.add("3.00-3.99");
        caratList.add("4.00-4.99");
        caratList.add("5.00-9.99");
        caratList.add("10.00-99.99");
    }

    private void clarityData() {
        clarityList = new ArrayList<String>();
        clarityList.add("FL");
        clarityList.add("IF");
        clarityList.add("VVS1");
        clarityList.add("VVS2");
        clarityList.add("VS1");
        clarityList.add("VS2");
        clarityList.add("SI1");
        clarityList.add("SI2");
        clarityList.add("SI3");
        clarityList.add("I1");
        clarityList.add("I2");
    }

    protected void colorData() {
        colorList = new ArrayList<String>();
        colorList.add("");
        colorList.add("D");
        colorList.add("E");
        colorList.add("F");
        colorList.add("G");
        colorList.add("H");
        colorList.add("I");
        colorList.add("J");
        colorList.add("K");
        colorList.add("L");
        colorList.add("M");
        colorList.add("N");
        popColorList = new ArrayList<>();
        popColorList.add("D");
        popColorList.add("E");
        popColorList.add("F");
        popColorList.add("G");
        popColorList.add("H");
        popColorList.add("I");
        popColorList.add("J");
        popColorList.add("K");
        popColorList.add("L");
        popColorList.add("M");
        popColorList.add("N");
    }

    private void international(final CalculatorRequest calculator) {
        if (NetUtil.isConnected(mContext) == false) {
            return;
        }
        final String path = Urls.ZHUBAOURL + Urls.UNIFORMOFFER + calculator.toJson();
        OkGo.<String>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                String lzyResponse=response.body();
                                JSONObject jsonObject=new JSONObject(lzyResponse);
                                int status=jsonObject.getInt("status");
                                String row=jsonObject.getString("msgdata");
                                ResultBean resultBean = new Gson().fromJson(row, ResultBean.class);
                                switch (status) {
                                    case 1:
                                        ArrayList<gold> golds= GsonUtil.StringJson(resultBean.getRows(), gold.class);
                                        if(null ==golds || golds.size() ==0){
                                            break;
                                        }
                                        onlinePriceList= new ArrayList<String>();
                                        for (int i = 0; i < golds.size(); i++) {
                                            if(golds.get(i).getPriceround() !=null){
                                                onlinePriceList.add(golds.get(i).getPriceround());
                                            }else if(golds.get(i).getPriceirregular() !=null){
                                                onlinePriceList= new ArrayList<String>();
                                                onlinePriceList.add(golds.get(i).getPriceirregular());
                                            }
                                        }
                                        ArrayList<String> onlinePrice = null;
                                        //设置布局管理器
                                        recyclerOnlinepriceColor.setLayoutManager(new LinearLayoutManager(mContext));
                                        //获取每个item的高度
                                        height = recyclerOnlinepriceColor.getHeight() / 12;
                                        OnlinePriceAdapter onlinePriceAdapter = new OnlinePriceAdapter(mContext, colorList, height, selectIndex);
                                        //设置adapter
                                        recyclerOnlinepriceColor.setAdapter(onlinePriceAdapter);

                                        //设置布局管理器
                                        recyclerOnlinepriceClarity.setLayoutManager(new GridLayoutManager(mContext, 11));
                                        onlinePriceAdapter = new OnlinePriceAdapter(mContext, clarityList, height, selectIndex);
                                        //设置adapter
                                        recyclerOnlinepriceClarity.setAdapter(onlinePriceAdapter);

                                        //设置布局管理器
                                        recyclerOnlineprice.setLayoutManager(new GridLayoutManager(mContext, 11));
                                        height = recyclerOnlineprice.getHeight() / 11;
                                        onlinePrice = new ArrayList<>();
                                        for (int i = 1; i <= onlinePriceList.size(); i++) {
                                            BigDecimal bigOnlierprice = new BigDecimal(onlinePriceList.get(i - 1));
                                            bigOnlierprice = bigOnlierprice.divide(new BigDecimal(100));
                                            onlinePrice.add(String.valueOf(bigOnlierprice));
                                        }
                                        onlinePriceAdapter = new OnlinePriceAdapter(mContext, onlinePrice, height, selectIndex);
                                        recyclerOnlineprice.setAdapter(onlinePriceAdapter);
                                        onlinePriceAdapter.setOnItemClickListener(new OnlinePriceAdapter.OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int tag, String data) {
                                            }
                                        });
                                        break;
                                    case -1:
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e("查询国际报价", e);
                        }
                    }
                });
    }


    //沉浸式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
