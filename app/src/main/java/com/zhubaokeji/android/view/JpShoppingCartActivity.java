package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.JpShoppingCartAdapter;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpShoppingCartResponse;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.fragment.MyStatusView;
import com.zhubaokeji.android.fragment.StatusLayoutFragment;
import com.zhubaokeji.android.listener.OnItemListener;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.ColorDialog;
import com.zhubaokeji.library.TitleBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/5/13.
 */

public class JpShoppingCartActivity extends BaseActivity {
    @BindView(R.id.netmoney)
    TextView netmoney;
    @BindView(R.id.rechargemoney)
    TextView rechargemoney;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_shar)
    LinearLayout llShar;
    @BindView(R.id.jp_shopping_placeOrder)
    TextView jpShoppingPlaceOrder;
    @BindView(R.id.jp_shopping_delete)
    TextView jpShoppingDelete;
    @BindView(R.id.jp_shopping_explain)
    TextView jpShoppingExplain;
    @BindView(R.id.jp_shoppingcart_title)
    TitleBar jpShoppingcartTitle;
    @BindView(R.id.jp_shopping_checkAll)
    CheckBox jpShoppingCheckAll;
    @BindView(R.id.jp_shopping_totalPrice)
    TextView jpShoppingTotalPrice;
    @BindView(R.id.offerPrice)
    TextView offerPrice;
    @BindView(R.id.offerCount)
    TextView offerCount;
    @BindView(R.id.recycler_ShoppingCart)
    SwipeMenuRecyclerView recyclerShoppingCart;
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;

    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    public static ArrayList<JpShoppingCartResponse> responseList = new ArrayList<JpShoppingCartResponse>();
    JpUserInfo jpUserInfo;
    JpShoppingCartAdapter jpShoppingCartAdapter;
    //记录Menu的状态
    private boolean isShow;
    private boolean isSelectAll;
    //记录选择的Item
    private HashSet<Integer> positionSet = new HashSet<>();;
    private int flag = 0;
    private BigDecimal totalPrice;// 购买的商品总价
    private BigDecimal bigOfferPrice; //优惠商品的总价
    private int totalCount = 0;// 购买的商品总数量
    private int offerTota = 0; //优惠商品总数量
    private TitleBar titleBar;
    ColorDialog shopDialog;
    private Activity mContext;
    MyStatusView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_shoppingcart);
        ButterKnife.bind(this);
            /*设置标题*/
        mContext = this;
        boolean isImmersive = true;
        initTranslucentStatusBar();
        titleBar = (TitleBar) findViewById(R.id.jp_shoppingcart_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setTitle("购物车");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(responseList.size(), intent);
                finish();
            }
        });
        titleBar.addAction(new TitleBar.TextAction("删除") {
            @Override
            public void performAction(View view) {
//                if (flag == 0) {
//                    llInfo.setVisibility(View.GONE);
//                    llShar.setVisibility(View.VISIBLE);
//                } else if (flag == 1) {
//                    llInfo.setVisibility(View.VISIBLE);
//                    llShar.setVisibility(View.GONE);
//                }
//                flag = (flag + 1) % 2;//其余得到循环执行上面2个不同的功能
                if (totalCount == 0) {
                    ToastUtil.show(getApplicationContext(), "您还没有选择钻石哦");
                    return;
                }
                shopDialog = new ColorDialog(view.getContext());
                shopDialog.setTitle("温馨提示");
                shopDialog.setAnimationEnable(true);
                shopDialog.setContentText("您确定要将这些商品从购物车中移除吗？");
//              dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
                shopDialog.setPositiveListener("确认", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        doDelete();
                        dialog.dismiss();
                    }
                })
                        .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                            @Override
                            public void onClick(ColorDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        recyclerShoppingCart.setLayoutManager(new LinearLayoutManager(this));

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        recyclerShoppingCart.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerShoppingCart.setSwipeMenuItemClickListener(mMenuItemClickListener);
        statusView = new MyStatusView(getApplicationContext());
        statusLayout.setStatusView(statusView);
        if(NetUtil.isConnected(this) != true) {
            statusLayout.showSetting();
            return;
        }

        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());

        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData();
        initInfo();
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    private void initInfo() {
        final String userInfoUrl = Urls.URL + Urls.JPUSERINFO + "&token=" + jpUserInfo.getToken();
        OkGo.<LzyResponse<JpUserInfo>>get(userInfoUrl)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<JpUserInfo>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<JpUserInfo>> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyResponse<JpUserInfo> lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        jpUserInfo=lzyResponse.getMsgdata();
                                        if (jpUserInfo != null) {
                                            netmoney.setText(jpUserInfo.getNetmoney());
                                            rechargemoney.setText(jpUserInfo.getRechargemoney());
                                        }
                                        break;
                                    case -404:
                                    case 404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(mContext, "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpShoppingCartActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(mContext, message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "JP购物车查询");
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse<JpUserInfo>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
    private void initData() {
        final String path = Urls.URL + Urls.JPSHOPPINGCART + "&token=" + jpUserInfo.getToken();
        OkGo.<LzyListResponse<ArrayList<JpShoppingCartResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<JpShoppingCartResponse>>>(this) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<JpShoppingCartResponse>>> response) {
                        try {
                            if(null !=response && null !=response.body()){
                                LzyListResponse<ArrayList<JpShoppingCartResponse>> lzyResponse=response.body();
                                String message=lzyResponse.message;
                                switch (lzyResponse.status){
                                    case 1:
                                        LzyListResponse<ArrayList<JpShoppingCartResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        responseList=GsonUtil.FromJson(serverModle.rows,JpShoppingCartResponse.class);
                                        if (responseList != null) {
                                            jpShoppingCartAdapter = new JpShoppingCartAdapter(R.layout.jp_shoppingcart_item, responseList);
                                            // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
                                            // 设置菜单创建器。
                                            recyclerShoppingCart.setSwipeMenuCreator(swipeMenuCreator);
                                            // 设置菜单Item点击监听。
                                            recyclerShoppingCart.setSwipeMenuItemClickListener(mMenuItemClickListener);
                                            recyclerShoppingCart.setAdapter(jpShoppingCartAdapter);
                                            titleBar.setTitle("购物车" + "(" + responseList.size() + ")");
                                            calculate();
                                            offerCalculate();
                                            setListener();
                                        }
                                        break;
                                    case -404:
                                    case 404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(mContext, "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpShoppingCartActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(mContext, message);
                                        break;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<JpShoppingCartResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(),FlagUtil.JP);
                    }
                });
    }

    private void setListener() {
        if (positionSet.size() == 0) {
            jpShoppingCheckAll.setChecked(false);
        }
        jpShoppingCartAdapter.setOnItemListener(new OnItemListener() {

            @Override
            public void onItemClick(int position, boolean isChecked) {
                responseList.get(position).setSelect(isChecked);
                if (!isChecked) {
                    if (responseList.get(position).isOfferSelect()) {
                        offerCompute(position, isChecked);
                        responseList.get(position).setOfferSelect(isChecked);
                    }
                }
                for (int i = 0; i < responseList.size(); i++) {
                    if (true == responseList.get(i).isSelect()) {
                        positionSet.add(i);
                    } else {
                        positionSet.remove(i);
                    }
                }
                if (positionSet.size() == responseList.size()) {
                    if (responseList.size() == 0) {
                        jpShoppingCheckAll.setChecked(false);
                    } else {
                        jpShoppingCheckAll.setChecked(true);
                    }
                } else {
                    jpShoppingCheckAll.setChecked(false);
                }
                jpShoppingCartAdapter.notifyDataSetChanged();
                offerCalculate();
                calculate();
            }

            @Override
            public void onItemReportClick(JpShoppingCartResponse jpShoppingCartResponse) {
                if (jpShoppingCartResponse !=null) {
                    Intent result_intent = new Intent();
                    result_intent.setClass(JpShoppingCartActivity.this, ZhengshuActivity.class);
                    result_intent.putExtra("reportNo", jpShoppingCartResponse.getReportno());
                    result_intent.putExtra("reportType", jpShoppingCartResponse.getReport());
                    startActivity(result_intent);
                }
            }

            // 优惠点击事件
            @Override
            public void onItemyOfferClick(int position, boolean isChecked) {
                responseList.get(position).setOfferSelect(isChecked);
                offerCompute(position, isChecked);
                offerCalculate();
                calculate();
                jpShoppingCartAdapter.notifyDataSetChanged();
            }
        });
    }


    @OnClick({R.id.jp_shopping_placeOrder, R.id.jp_shopping_delete, R.id.jp_shopping_explain, R.id.jp_shopping_checkAll})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.jp_shopping_placeOrder:
                final List<JpShoppingCartResponse> pendingOrders = new ArrayList<JpShoppingCartResponse>();// 待下单
                final StringBuffer idsBuffer = new StringBuffer();
                final StringBuffer offerBuffer = new StringBuffer();
                for (int i = 0; i < responseList.size(); i++) {
                    JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
                    if (jpShoppingCartResponse.isSelect()) {
                        pendingOrders.add(jpShoppingCartResponse);
                        idsBuffer.append(jpShoppingCartResponse.getRecno()).append(",");
                        if (jpShoppingCartResponse.isOfferSelect()) {
                            offerBuffer.append(jpShoppingCartResponse.getRecno()).append(",");
                        }
                    }
                }
                if (pendingOrders.size() == 0) {
                    ToastUtil.show(getApplicationContext(), "您还没有勾选钻石!");
                    break;
                }
                BigDecimal bigRechargemoney = new BigDecimal(jpUserInfo.getRechargemoney());
                if (bigRechargemoney.compareTo(bigOfferPrice) == -1) {
                    ToastUtil.show(getApplicationContext(), "会员余额不足,请充值");
                    break;
                }
                String message="";
                int hk=0;
                int ind=0;
                ColorDialog dialog = new ColorDialog(mContext);
                dialog.setTitle("确认下单");
                dialog.setAnimationEnable(true);
                for (int i = 0; i <pendingOrders.size(); i++) {
                    JpShoppingCartResponse  jpShopCarResponse=pendingOrders.get(i);
                    if(jpShopCarResponse.getAddress().equals("HK")){
                        hk+=1;
                    }else if (jpShopCarResponse.getAddress().equals("ind")){
                        ind+=1;
                    }
                    if(ind >0){
                        message="您选择的商品有"+hk+"颗在hk,"+ind+"颗在ind,预计3-7个工作日到港";
                    }else {
                        message="您选择的商品有"+hk+"颗在hk,"+ind+"颗在ind";
                    }
                }
                dialog.setContentText(message);
//              dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
                dialog.setPositiveListener("取消", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                })
                        .setNegativeListener("下单", new ColorDialog.OnNegativeListener() {
                            @Override
                            public void onClick(ColorDialog dialog) {
                                responseList.removeAll(pendingOrders);
                                jpShoppingCartAdapter.notifyDataSetChanged();
                                placeOrder(idsBuffer,offerBuffer);
                                //记得重新设置购物车
                                calculate();
                                offerCalculate();
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.jp_shopping_delete:
//                if (totalCount == 0) {
//                    Toast.makeText(getApplicationContext(), "请选择要移除的商品", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                alertDialog = new AlertDialog(view.getContext(), R.style.dialog);
//                alertDialog.setTitleText("温馨提示");
//                alertDialog.setNameText("您确定要将这些商品从购物车中移除吗？");
//                alertDialog.show();
//                alertDialog.setCanceledOnTouchOutside(true);
//                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.setOnItemClickListener(new AlertDialog.OnDialogItemClickListener() {
//                    @Override
//                    public void onItemClick(View view) {
//                        doDelete();
//                    }
//                });
                break;
            case R.id.jp_shopping_explain:
                break;
            case R.id.jp_shopping_checkAll:
                for (int i = 0; i < responseList.size(); i++) {
                    positionSet.add(i);
                    responseList.get(i).setSelect(jpShoppingCheckAll.isChecked());
                    if (!jpShoppingCheckAll.isChecked()) {
                        positionSet=new HashSet<>();
                        if (responseList.get(i).isOfferSelect()) {
                            offerCompute(i, false);
                        }
                        responseList.get(i).setOfferSelect(jpShoppingCheckAll.isChecked());
                    }
                }
                jpShoppingCartAdapter.notifyDataSetChanged();
                calculate();
                offerCalculate();
                break;
        }
    }
    void placeOrder(StringBuffer idsBuffer, StringBuffer offerBuffer){
        final String path = Urls.URL + Urls.JPSHOPPINGCARTPLACEORDER + "&token=" + jpUserInfo.getToken() + "&ids=" + idsBuffer.toString() + "&offsalelist=" + offerBuffer.toString();
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
                                        dialogShow(mContext, message);
                                        initData();
                                        break;
                                    case -1:
                                        ToastUtil.show(mContext, message);
                                        break;
                                    case 404:
                                    case -404:
                                        jp_Login_Boolean = false;
                                        ToastUtil.show(mContext, "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpShoppingCartActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "jp购物车下单");
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(),FlagUtil.JP);
                    }
                });
    }
    void dialogShow(final Context context, String message) {
        ColorDialog dialog = new ColorDialog(context);
        dialog.setTitle("提示");
        dialog.setAnimationEnable(true);
        dialog.setContentText(message);
//        dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
        dialog.setPositiveListener("前往订单", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                startActivity(new Intent(context, JpOrderActivity.class));
                dialog.dismiss();
            }
        })
                .setNegativeListener("留在此页", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 优惠统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    void offerCalculate() {
        bigOfferPrice = new BigDecimal("0");
        offerTota = 0;
        for (int i = 0; i < responseList.size(); i++) {
            JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
            if (jpShoppingCartResponse.isOfferSelect()) {
                offerTota++;
                BigDecimal salepriceTotal = new BigDecimal(jpShoppingCartResponse.getSaleprice());
                bigOfferPrice = bigOfferPrice.add(salepriceTotal).setScale(0, BigDecimal.ROUND_HALF_UP);
            }
        }
        offerCount.setText(offerTota + "");
        offerPrice.setText("$" + bigOfferPrice.toString());
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */

    private void calculate() {
        totalCount = 0;
        totalPrice = new BigDecimal("0.00");
        for (int i = 0; i < responseList.size(); i++) {
            JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
            if (jpShoppingCartResponse.isSelect()) {
                totalCount++;
                BigDecimal bigSaleprice = new BigDecimal(jpShoppingCartResponse.getSaleprice());
                totalPrice = totalPrice.add(bigSaleprice).setScale(0, BigDecimal.ROUND_HALF_UP);
            }

        }
        jpShoppingTotalPrice.setText("￥" + totalPrice);
        jpShoppingPlaceOrder.setText("确认下单(" + totalCount + ")");
        //计算购物车的金额为0时候清空购物车的视图
        if (totalCount == 0) {
            setCartNum();
        } else {

        }
    }

    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < responseList.size(); i++) {
//            responseList.get(i).setSelect(jpShoppingCheckAll.isChecked());
//            JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
            for (JpShoppingCartResponse jpShoppingCartResponse : responseList) {
                count += 1;
            }
        }

        //购物车已清空
        if (count == 0) {
            clearCart();
        } else {
            titleBar.setTitle("购物车" + "(" + responseList.size() + ")");
        }
    }

    private void clearCart() {
        titleBar.setTitle("购物车" + "(" + 0 + ")");
    }


    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        List<JpShoppingCartResponse> toBeDeleteGroups = new ArrayList<JpShoppingCartResponse>();// 待删除的组元素列表
        StringBuffer idsBuffer = new StringBuffer();
        for (int i = 0; i < responseList.size(); i++) {
            JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
            if (jpShoppingCartResponse.isSelect()) {
                toBeDeleteGroups.add(jpShoppingCartResponse);
                idsBuffer.append(jpShoppingCartResponse.getRecno());
                idsBuffer.append(",");
            }
        }
        responseList.removeAll(toBeDeleteGroups);
        if (responseList.size() == 0) {
            jpShoppingCheckAll.setChecked(false);
        }
        final String path = Urls.URL + Urls.JPSHOPPINGCARTDELECT + "&token=" + jpUserInfo.getToken() + "&ids=" + idsBuffer.toString();
        OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                    @Override
                    public void onSuccess(Response<LzyResponse> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyResponse lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        offerCalculate();
                                        calculate();
                                        setListener();
                                        break;
                                    case -1:
                                    case 0:
                                        //GSON直接解析成对象
                                        ToastUtil.show(getApplicationContext(), message);
                                        initData();
                                        break;
                                    case 404:
                                    case -404:
                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
                                        Intent login_intent = new Intent();
                                        login_intent.setClass(JpShoppingCartActivity.this, JpLoginActivity.class);
                                        startActivity(login_intent);
                                        break;
                                    default:
                                        ToastUtil.show(getApplicationContext(), message);
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "jp购物车删除");
                        }
                    }
                    @Override
                    public void onError(Response<LzyResponse> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(),FlagUtil.JP);
                    }
                });
        //记得重新设置购物车
        offerCalculate();
        calculate();
        jpShoppingCartAdapter.notifyDataSetChanged();

    }


    private void offerCompute(int position, boolean isChecked) {
        JpShoppingCartResponse shoppingCartResponse = responseList.get(position);
        BigDecimal bigEndsaleback = new BigDecimal(shoppingCartResponse.getEndsaleback());
        BigDecimal bigOffpoint = new BigDecimal(jpUserInfo.getOffpoint());
        BigDecimal bigSaleprice = new BigDecimal(shoppingCartResponse.getSaleprice());
        BigDecimal bigOnlineprice = new BigDecimal(shoppingCartResponse.getOnlineprice());
        BigDecimal bigCarat = new BigDecimal(shoppingCartResponse.getCarat());
        if (isChecked) {
            bigEndsaleback = bigEndsaleback.add(bigOffpoint);
        } else {
            bigEndsaleback = bigEndsaleback.subtract(bigOffpoint);
        }
        responseList.get(position).setEndsaleback(bigEndsaleback.toString());
        bigSaleprice = bigOnlineprice.multiply(bigCarat).multiply((new BigDecimal(100).add(bigEndsaleback)).divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
        responseList.get(position).setSaleprice(bigSaleprice.toString());
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_width);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.BLACK)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (menuPosition == 0) {// 删除按钮被点击。
                final String recnoStr = responseList.get(adapterPosition).getRecno();
                responseList.remove(adapterPosition);
                positionSet.remove(adapterPosition);
                jpShoppingCartAdapter.notifyItemRemoved(adapterPosition);
                final String path = Urls.URL + Urls.JPSHOPPINGCARTDELECT + "&token=" + jpUserInfo.getToken() + "&ids=" + recnoStr;
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
                                                offerCalculate();
                                                calculate();
                                                setListener();
                                                break;
                                            case -1:
                                            case 0:
                                                //GSON直接解析成对象
                                                ToastUtil.show(getApplicationContext(), message);
                                                initData();
                                                break;
                                            case 404:
                                            case -404:
                                                ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
                                                Intent login_intent = new Intent();
                                                login_intent.setClass(JpShoppingCartActivity.this, JpLoginActivity.class);
                                                startActivity(login_intent);
                                                break;
                                            default:
                                                ToastUtil.show(getApplicationContext(), message);
                                                break;
                                        }
                                    }
                                } catch (Exception e) {
                                    Logger.e(e, "jp购物车删除");
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
    };

}
